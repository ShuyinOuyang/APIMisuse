#!/usr/bin/env python
# -*- coding:utf-8 -*-
# @Time  : 06/03/2020 18:36
# @Author: ouyangshuyin
# @File  : vocab.py
# @Description: building of vocab
import os
import re
import random
import numpy as np

def buildVocabulary(dic):
    vocabulary = []
    for value in dic.values():
        vocabulary.append(value)
    vocabulary = list(set(vocabulary))
    return vocabulary

def dot2Node_Edge(file):
    node = {}
    edge = []
    notlist = ['&', '+', '-', '=', '>>>', '|', '{}', '<a>', '<r>']
    for line in file:
        flag1 = re.search('\[ label', line) is not None
        obj = re.sub(r'\[.*;', "", line)
        flag2 = re.search(r'->', obj) is not None
        pattern = re.compile(r'\d+')
        label = re.findall('.*label=\"(.*)\" shape=.*', line)
        if flag1 and not flag2:
            if label[0] in notlist:
                return {}, []
            node[int(pattern.findall(obj)[0])] = label[0]
        elif flag1 and flag2:
            res = pattern.findall(obj)
            edge.append([int(res[0]),int(res[1])])
    return node, edge

def get_FileSize(filePath):
    fsize = os.path.getsize(filePath)
    fsize = fsize / float(1024)

    return round(fsize, 2)

if __name__ == "__main__":
    inputPath = "F:\\project\\train_data"
    outputPath0 = "node outputPath"
    outputPath1 = "edge outputPath"
    outputPath2 = "vocabulary outputPath"
    voc_all = []
    node_all = []
    edge_all = []
    node = {}
    edge = []
    num = 0
    for home, dirs, files in os.walk(inputPath):
        for filename in files:
            if get_FileSize(os.path.join(home, filename)) < 10:
                path_node = os.path.dirname(os.path.join(home, filename)).replace("0_CipherInputStreamTest", "graph") + "\\node"
                path_edge = os.path.dirname(os.path.join(home, filename)).replace("0_CipherInputStreamTest", "graph") + "\\edge"
                print(os.path.join(home, filename))
                with open(os.path.join(home, filename), 'r', encoding='ISO-8859-1') as f:
                    node, edge = dot2Node_Edge(f)
                    # for i in node:
                    #     file1 = open(path_node + "\\" + os.path.splitext(filename)[0] + ".txt", 'a')
                    #     file1.write(str(i) + '\n')
                    #     file1.close()
                    voc = buildVocabulary(node)
                voc_all = list(set(voc_all + voc))
                if node != {} and edge != []:
                    node_all.append(node)
                    edge_all.append(edge)
                    if num==5000:
                        break
                    else:
                        num = num+1
    # 写入词典
    voc_all.sort()
    for i in voc_all:
        f = open(outputPath2, 'a')
        f.write(str(i)+'\n')
        f.close()
    for i in node_all:
        f = open(outputPath0, 'a')
        f.write(str(i)+'\n')
        f.close()
    for i in edge_all:
        f = open(outputPath1, 'a')
        f.write(str(i)+'\n')
        f.close()
