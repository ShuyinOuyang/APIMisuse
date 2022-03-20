#!/usr/bin/env python
# -*- coding:utf-8 -*-
# @Time  : 06/03/2020 22:50
# @Author: ouyangshuyin
# @File  : model.py
# @Description: model of API Misuse Detection


import os

# from gensim.models import word2vec
from random import random

from tensorflow import ConfigProto

os.environ["CUDA_VISIBLE_DEVICES"] = "0"

import tensorflow as tf
from tensorflow.contrib.rnn import LSTMCell, LSTMStateTuple
from tensorflow.python.util import nest
from sklearn.utils import shuffle
from sklearn.model_selection import train_test_split
import matplotlib.pyplot as plt
import numpy as np
from vocab import Vocab
import os
import time
import pickle

VOCAB_SIZE = 1564
TRAIN_DATA = "train_data.txt"           # 训练数据路径
TRAIN_LABEL = "train_label.txt"         # 训练数据路径
VOCAB_PATH = "vocab.txt"                # 词表路径
OUTPUT_PATH = ""       # 输出路径

MAX_SEQ_LEN = None
SHARE_EMB_AND_SOFTMAX = False
MAX_GRAD_NORM = 5
EMBEDDING_LEARNING_RATE_FACTOR = 0.1
KEEP_PROB = 0.8                         # Keep_prob
BATCH_SIZE = 64                        # batch大小

HIDDEN_SIZE = 100                        # 隐层大小
NUM_LAYER = 2                            # LSTM层数
lr = 0.002                               # 学习率
NUM_EPOCH = 20                           # 迭代次数

OUTPUT_PATH = OUTPUT_PATH + "New_v="+ str(VOCAB_SIZE) +"h="+ str(HIDDEN_SIZE) +"l="+ str(NUM_LAYER) +"b="+ str(BATCH_SIZE) +"e="+ str(NUM_EPOCH) +"lr="+ str(lr) + "/"

if not os.path.exists(OUTPUT_PATH):
    os.makedirs(OUTPUT_PATH)


MODEL_PATH = OUTPUT_PATH + "model"      # 模型输出
LOG_PATH = OUTPUT_PATH + "log"          # log输出

# vocab = Vocab(VOCAB_PATH)
# weight_matrix = np.random.uniform(-0.05, 0.05, (VOCAB_SIZE, 50)).astype(np.float32)

# 定义可以忽略0因素的LSTM
class ComLSTM(object):

    def __init__(self):
        self.inputs = tf.placeholder(shape=(None, None, 10), dtype=tf.float32, name='inputs')
        self.inputs_length = tf.placeholder(shape=(None,), dtype=tf.float32, name='inputs_length')
        self.labels = tf.placeholder(shape=(None,), dtype=tf.int64, name='labels')

        # 定义LSTM单元, NUM_LAYER 为多层LSTM的层数
        self.cell = tf.contrib.rnn.MultiRNNCell(
            [tf.contrib.rnn.BasicLSTMCell(HIDDEN_SIZE)
             for _ in range(NUM_LAYER)])

        # 定义embedding矩阵
        # self.embedding = tf.Variable(initial_value=weight_matrix, dtype=tf.float32)

    def forward(self):

        batch_size = BATCH_SIZE

        # 将输入id转换为词向量
        # embedding = tf.nn.embedding_lookup(self.embedding, self.inputs)
        # print(embedding)
        # # 进行dropout
        # embedding = tf.nn.dropout(embedding, KEEP_PROB)

        conv1 = tf.layers.conv1d(
            inputs=self.inputs,
            filters=2,
            kernel_size=2,
            strides=1,
            padding='same',
            activation=tf.nn.relu
        )

        # 使用dynamic_rnn
        # with with tf.variable_scope("lstm"):
        with tf.variable_scope("dynamic_rnn"):
            outputs, last_state = tf.nn.dynamic_rnn(
                self.cell,
                conv1,
                self.inputs_length,
                dtype=tf.float32)

            # 在计算平均损失时，需要将填充位置的权重设置为0，以避免无效位置的预测干扰
            # 这里的 last_state 是有输入的最后一个h输出，因此可以直接使用这个输出来作为计算损失的newoutputs
            # 因为有使用了dynamic，有部分的output会置零
            print(last_state)
            newoutputs = last_state[-1][1]

        # logits = tf.matmul(newoutputs,self.softmax_weight) + self.softmax_bias
        logits = tf.layers.dense(newoutputs, VOCAB_SIZE)

        loss = tf.nn.sparse_softmax_cross_entropy_with_logits(logits=logits, labels=self.labels)

        # 定义损失函数
        cost = tf.reduce_mean(loss)
        tf.summary.scalar("cost", cost)

        # 计算正确率
        accuracy = tf.equal(tf.argmax(logits, 1), self.labels)
        evaluation = tf.reduce_mean(tf.cast(accuracy, tf.float32))
        tf.summary.scalar("top1_accuracy", evaluation)

        # 定义反向传播操作
        # trainable_variables = tf.trainable_variables()

        # 控制梯度大小，定义优化方法和训练步骤
        # grads = tf.gradients(cost / tf.to_float(batch_size), trainable_variables)

        #         grads, _ = tf.clip_by_global_norm(grads, .MAX_GRAD_NORM)
        #         optimizer = tf.train.AdamOptimizer(learning_rate=lr)

        #         train_op = optimizer.apply_gradients(zip(grads, trainable_variables))

        # opt = tf.train.AdamOptimizer(learning_rate=lr)
        opt = tf.train.GradientDescentOptimizer(learning_rate=lr)
        grads_and_vars = opt.compute_gradients(loss)
        clip_grads = [(tf.clip_by_value(grad, -1., 1.), var) for grad, var in grads_and_vars if grad is not None]

        # found = 0
        # for i, (grad, var) in enumerate(clip_grads):
        #     if var == self.embedding:
        #         found += 1
        #         grad = tf.scalar_mul(EMBEDDING_LEARNING_RATE_FACTOR, grad)
        #         clip_grads[i] = (grad, var)
        # assert found == 1  # internal consistency check

        train_op = opt.apply_gradients(clip_grads)

        return self.inputs, self.inputs_length, self.labels, cost, train_op, evaluation


def getBatch(dataset, labelset, batchsize):
    total = int(len(dataset) / batchsize)
    if len(dataset) % batchsize > 0:
        total += 1
    for i in range(total):
        tempdata = dataset[i * batchsize:(i + 1) * batchsize]
        templabel = labelset[i * batchsize:(i + 1) * batchsize]
        yield tempdata, templabel


def batch_major(inputs, max_sequence_length=None):
    sequence_lengths = [len(seq) for seq in inputs]
    batch_size = len(inputs)

    if max_sequence_length is None:
        max_sequence_length = max(sequence_lengths)

    inputs_batch_major = np.zeros(shape=[batch_size, max_sequence_length, 10], dtype=np.float32)  # == PAD

    for i, seq in enumerate(inputs):
        for j, element in enumerate(seq):
            inputs_batch_major[i, j] = element
    return inputs_batch_major, sequence_lengths

def load_data():
    trainingdata = []
    traininglabel = []
    validdata = []
    validlabel = []
    # model = word2vec.Word2Vec.load('gnn/res_lstm.model')
    # with open(TRAIN_DATA, "r") as fd:
    # with open(TRAIN_LABEL, "r") as fl:
    #     # for line in fd.readlines():
    #     #     trainingdata.append([model[w] for w in line.split()])
    #         # trainingdata.append([int(w) for w in line.split()])
    #     for line in fl.readlines():
    #         traininglabel.append(float(line))
    # pickle_file = open("gnn/traindata1.pkl", "rb")
    # trainingdata = pickle.load(pickle_file)
    # # 加入验证集提取，验证集的大小为训练集的10%
    # trainingdata, validdata, traininglabel, validlabel = train_test_split(
    #     trainingdata, traininglabel, test_size=0.1, random_state=42)#42

    with open("train_data_label.txt", "r") as ftrain:
        with open("test_data_label.txt", "r") as ftest:
            for line in ftrain.readlines():
                traininglabel.append(float(line))
            for line in ftest.readlines():
                validlabel.append(float(line))
    pickle_file = open("gnn/train_test_data.pkl", "rb")
    trainingdata = pickle.load(pickle_file)
    validdata = pickle.load(pickle_file)
    # temp = list(zip(trainingdata, traininglabel))
    # random.shuffle(temp)
    # trainingdata[:], traininglabel[:] = zip(*temp)
    return trainingdata, traininglabel, validdata, validlabel



# %%

# 使用给定的模型训练结果
def run_epoch(session, cost, train_op, inputs, inputs_length, labels, accuracy, saver):
    # 准备写入日志
    summary = tf.summary.merge_all()
    writer = tf.summary.FileWriter(LOG_PATH, tf.get_default_graph())

    losses = []
    validlosses = []
    validaccuracies = []
    global_step = 0
    train_feed_dict = {}
    valid_feed_dict = {}

    # 读取训练数据
    read_start = time.time()
    print('read data_set')
    trainingdata, traininglabel, valid_data, valid_label = load_data()

    # 对验证集进行补零的操作先
    valid_data, valid_length = batch_major(valid_data)
    print('completed read data_set, time: %.4f' % (time.time() - read_start))

    save_path = MODEL_PATH
    dataCount = 0

    for num in range(NUM_EPOCH):
        numOfEpoch = 0
        tempCount = 0
        start0 = time.time()

        dataset_temp, label_temp = shuffle(trainingdata, traininglabel)

        for databatch, batchlabel in getBatch(dataset_temp, label_temp, BATCH_SIZE):
            start = time.time()
            # 获得每一个batch下的训练数据以及对应数据的len
            databatch, length = batch_major(databatch)
            train_feed_dict[inputs] = databatch
            train_feed_dict[inputs_length] = length
            train_feed_dict[labels] = batchlabel

            _, batchloss, batchaccuracy, summaries = session.run([train_op, cost, accuracy, summary], train_feed_dict)

            numOfEpoch += 1
            global_step += 1

            # summary
            writer.add_summary(summaries, global_step)

            if global_step % 1000 == 1:
                losses.append(batchloss)
            end = time.time()

            print('batch %d---%d, loss: %.3e, accuracy: %.3e, time: %f' % (
            num + 1, numOfEpoch, batchloss, batchaccuracy, end - start))

        end0 = time.time()
        print('epoch:%d, time:%f' % (num + 1, end0 - start0))

        valid_feed_dict[inputs] = valid_data
        valid_feed_dict[inputs_length] = valid_length
        valid_feed_dict[labels] = valid_label

        validloss, validaccuracy = session.run([cost, accuracy], valid_feed_dict)

        validlosses.append(validloss)
        validaccuracies.append(validaccuracy)
        print('epoch:%d, valid_loss: %.3e, valid_accuracy: %.3e' % (num + 1, validloss, validaccuracy))

    # 保存日志 ================
    writer.flush()
    writer.close()

    checkpoint_path = saver.save(session, save_path, global_step=1)
    print('model saved in file: %s' % checkpoint_path)

    return losses, validlosses, validaccuracies

# 训练代码 ================
tf.reset_default_graph()
train_model = ComLSTM()
inputs, length, labels, cost, train_op, accuracy = train_model.forward()

print('start training...')


init = tf.global_variables_initializer()
saver = tf.train.Saver()
config = ConfigProto()
config.gpu_options.allow_growth = True
sess= tf.InteractiveSession(config=config)
# sess = tf.InteractiveSession()
sess.run(init)
losses, validlosses, validaccuracies = run_epoch(sess, cost, train_op, inputs, length, labels, accuracy, saver)
sess.close()
#%%


x = []
y = validlosses
for i in range(len(validlosses)):
    x.append(i)
#X轴，Y轴数据
plt.figure(figsize=(10,6)) #创建绘图对象
plt.plot(x,y,"b--",linewidth=1)   #在当前绘图对象绘图（X轴，Y轴，蓝色虚线，线宽度）
plt.xlabel("Epoch") #X轴标签
plt.ylabel("Test Loss")  #Y轴标签
plt.title("Test Loss") #图标题
plt.show()  #显示图


x = []
y = validaccuracies
for i in range(len(validaccuracies)):
    x.append(i)
#X轴，Y轴数据
plt.figure(figsize=(10,6)) #创建绘图对象
plt.plot(x,y,"b--",linewidth=1)   #在当前绘图对象绘图（X轴，Y轴，蓝色虚线，线宽度）
plt.xlabel("Epoch") #X轴标签
plt.ylabel("Test Accuracy")  #Y轴标签
plt.title("Test Accuracy") #图标题
plt.show()  #显示图
