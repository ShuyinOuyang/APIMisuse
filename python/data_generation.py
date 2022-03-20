#!/usr/bin/env python
# -*- coding:utf-8 -*-
# @Time  : 15/03/2020 16:27
# @Author: ouyangshuyin
# @File  : data_generation.py
# @Description : Data Generation
import os
import re
import pickle
import random
import numpy as np
import pandas as pd

def voc2num():
    file = open("F:\\vocabulary\\voc.txt")
    row = file.readlines()
    for i in range(len(row)):
        row[i] = re.sub(r'\n', "", str(row[i]))
    num = [i for i in range(len(row))]
    # for i in zip(row, num):
    #     print(i)

    file.close()
    return dict(zip(row, num))

def dataTrans(path, list_data):
    dic = voc2num()
    file = open(path)
    row = file.readlines()
    for line in row:
        # print(line)
        line = re.sub(r',', '', line)
        line = re.sub(r"\['", '', line)
        line = re.sub(r"']", '', line)
        line = re.sub(r"'", '', line)
        # print(line)
        line = list(line.strip().split(' '))
        s = []
        for i in line:
            s.append(dic[i])
        list_data.append(s)
    file.close()
    return list_data

def doTrans():
    inputPath = "F:\\sequence1"
    list_data = []
    for home, dirs, files in os.walk(inputPath):
        for filename in files:

            print(os.path.join(home, filename))
            list_data = dataTrans(os.path.join(home, filename), list_data)
            print(len(list_data))
            # file1 = open('F:\\num\\data.pkl', 'ab')
            # pickle.dump(list_data, file1)
            # file1.close()
            for i in list_data:
                with open('F:\\num\\data.txt', 'a') as file1:
                    file1.write(str(i) + '\n')
    print(len(list_data))


def produceData():
    data = []
    train1 = []
    label1 = []
    label2 = []
    label3 = []
    validate2 = []
    test3 = []
    max_len = 0
    train_lens = []

    with open("F:\\num\\data10.txt", "r") as file:
        row = file.readlines()
        # print(row[0])
        for line in row:
            # print(line)
            line = re.sub(r',', '', line)
            line = re.sub(r"\[", '', line)
            line = re.sub(r"]", '', line)
            line = re.sub(r"'", '', line)
            # print(line)
            line = list(line.strip().split(' '))
            s = []
            for i in line:
                s.append(i)
            data.append(s)
    # print(data[0])
    random.shuffle(data)
    for i in data:
        if len(i) > max_len:
            max_len = len(i)
    for index, value in enumerate(data): #enumerate
        # temp = []
        #         # for i in value:
        #         #     a = list(i)
        #         #     a = [int(i) for i in a]
        #         #     temp.append(a)
        #         #     while len(a) != 4:
        #         #         a.insert(0, 0)
        if index/len(data) < 0.6:
            for i,v in enumerate(value):
                if i == 0:
                    pass
                else:
                    label1.append(v)
                    train1.append(value[0:i])
        elif 0.6<=index/len(data) < 0.7:
            for i, v in enumerate(value):
                if i == 0:
                    pass
                else:
                    label2.append(v)
                    validate2.append(value[0:i])
        else:
            for i, v in enumerate(value):
                if i == 0:
                    pass
                else:
                    label3.append(v)
                    test3.append(value[0:i])

    for i, v in enumerate(train1):
        train_lens.append(len(v))
        train1[i] = supply1(v, max_len)
    for i, v in enumerate(validate2):
        validate2[i] = supply1(v, max_len)
    for i, v in enumerate(test3):
        test3[i] = supply1(v, max_len)

    # train1 = np.array(train1)
    # label1 = np.array(label1)
    # train = Train(train1, label1)
    # # validate2 = np.array(validate2)
    # # label2 = np.array(label2)
    # validate = Validate(validate2, label2)
    # # test3 = np.array(test3)
    # # label3 = np.array(label3)
    # test = Test(test3, label3)
    # api = Data(train, validate, test, train_lens, max_len)

    arr2csv1(train1, 'F:\\num\\data\\train_data.csv')
    arr2csv2(label1, 'F:\\num\\data\\train_label.csv')
    arr2csv1(validate2, 'F:\\num\\data\\validate_data.csv')
    arr2csv2(label2, 'F:\\num\\data\\validate_label.csv')
    arr2csv1(test3, 'F:\\num\\data\\test_data.csv')
    arr2csv2(label3, 'F:\\num\\data\\test_label.csv')
    # file1 = open('F:\\num\\api2.pkl', 'ab')
    # pickle.dump(api, file1)
    # file1.close()

def trans(s):
    b = []
    for j in s:
        temp = []
        for i in j:
            a = list(i)
            a = [int(i) for i in a]
            temp.append(a)
            while len(a) != 4:
                a.insert(0, 0)
        b.append(temp)
    return b

class Data:
    def __init__(self, train, validate, test, train_lens, max_len):
        self.train = train
        self.validate = validate
        self.test = test
        self.train_lens = train_lens
        self.max_lens = max_len

class Train:
    def __init__(self, data, label):
        self.data = data
        self.label = label

class Validate:
    def __init__(self, data, label):
        self.data = data
        self.label = label

class Test:
    def __init__(self, data, label):
        self.data = data
        self.label = label

def List1D(newlist):
    d = []
    for element in newlist:
        if not isinstance(element, list):
            d.append(element)
        else:
            d.extend(List1D(element))
    return d

def supply(l,max_len):
    while len(l)<max_len:
        l.append([0, 0, 0, 0])
    l = List1D(l)
    return l

def supply1(l,max_len):
    while len(l)<max_len:
        l.append([0])
    l = List1D(l)
    return l

def arr2csv1(a, s):
    name = ['f' + str(i) for i in range(17)]
    pd_data = pd.DataFrame(columns=name, data=a)
    pd_data.to_csv(s)

def arr2csv2(a, s):
    name = ['f' + str(i) for i in range(1)]
    pd_data = pd.DataFrame(columns=name, data=a)
    pd_data.to_csv(s)

if __name__ == "__main__":
    produceData()