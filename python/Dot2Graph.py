#!/usr/bin/env python
# -*- coding:utf-8 -*-
# @Time  : 09/03/2020 22:30
# @Author: ouyangshuyin
# @File  : Dot2Graph.py
# @Description : Transfer the .dot file to graph data structure
import os
import re
import random
import numpy as np

def D2G(file):
    G = {}
    G2 = {}
    G1 = []
    dict = {}

    # file = open(path)
    flag3 = True
    for line in file:
        flag1 = re.search('\[ label', line) is not None
        obj = re.sub(r'\[.*;', "", line)
        flag2 = re.search(r'->', obj) is not None
        pattern = re.compile(r'\d+')
        label = re.findall('.*label=\"(.*)\" shape=.*', line)
        if flag1 and not flag2:
            dict[pattern.findall(obj)[0]] = label[0]
            # print(obj)
            # print(label)
        elif flag1 and flag2 and flag3:
            for key in dict:
                G[dict[key]] = {}
                G2[key] =[]
            res = pattern.findall(obj)
            G[dict[res[0]]][dict[res[1]]] = 1

            Z = np.zeros((len(dict)+1, len(dict)+1))
            G1 = Z.tolist()
            G1[int(res[0])][int(res[1])] = 1

            G2[res[0]].append(str(res[1]))
            flag3 = False
        elif flag1 and flag2 and not flag3:
            res = pattern.findall(obj)
            # print(obj)
            G[dict[res[0]]][dict[res[1]]] = 1
            G1[int(res[0])][int(res[1])] = 1
            G2[res[0]].append(str(res[1]))
    # print(node)
    return G, G1, G2, dict


def randomWalk(G, step):
    route = []
    start = random.choice(list(G.keys()))
    # print(type(start))
    route.append(start)
    # print(route[0])
    for i in range(step):
        print(i)
        if not len(G[route[i]]) == 0:
            print(G[route[i]])
            next = random.choice(list(G[route[i]].keys()))
            route.append(next)
        else:
            break
    print(route)
    # for i in range(step):
    #
    # print(1)
#
def StartandEnd(G):
    start = []
    end = []
    route = []
    G1 = np.array(np.mat(G).T)
    for i in range(len(G)):
        a = G[:][i]
        # print(type(a))

        flag1 = isthere(a)
        if not flag1 and not i==0:
            end.append(i)

    for i in range(len(G1)):
        a = G1[:][i]
        # print(type(a))
        flag1 = isthere(a)
        if not flag1 and not i==0:
            start.append(i)

    return start, end
    # print(type(start[0]))
    # print(end)


def isthere(a):
    flag1 = False
    for j in range(len(a)):
        if a[j] == 0.0:
            pass
        else:
            flag1 = True
            break
    return flag1

def findAllPath(graph, start, end, path=[]):
    path = path + [start]
    if start == end:
        return [path]

    paths = []  # 存储所有路径
    for node in graph[start]:
        if node not in path:
            newpaths = findAllPath(graph, node, end, path)
            for newpath in newpaths:
                paths.append(newpath)
    return paths

def collectPath(start, end, G):
    l = []
    for i in start:
        for j in end:
            l = l+findAllPath(G, str(i), str(j))
    return l

def Dot2Sequence(file):
    G, G1, G2, dic = D2G(file)
    # 词汇表合并
    voc =[]
    voc = list(set(voc + buildVocabulary(dic)))
    # print(dic)
    start, end = StartandEnd(np.array(G1))
    res = collectPath(start, end, G2)
    for i in range(len(res)):
        for j in range(len(res[i])):
            res[i][j] = dic[res[i][j]]
    return res, voc

# 构建词汇表
def buildVocabulary(dic):
    vocabulary = []
    for value in dic.values():
        vocabulary.append(value)
    vocabulary = list(set(vocabulary))
    return vocabulary


def get_FileSize(filePath):
    fsize = os.path.getsize(filePath)
    fsize = fsize / float(1024)

    return round(fsize, 2)


if __name__ == "__main__":
    inputPath = "F:\\project\\train_data"
    # outputPath2 = "F:\\project\\vocabulary\\voc.txt"
    voc_all = []
    res_all = []
    num = 0
    for home, dirs, files in os.walk(inputPath):
        for filename in files:
            if get_FileSize(os.path.join(home, filename)) < 10:
                path = os.path.dirname(os.path.join(home, filename)).replace("train_data", "graph_5000")
                if not os.path.exists(path):
                    os.makedirs(path)
                else:
                    continue
                print(os.path.join(home, filename))
                with open(os.path.join(home, filename), 'r', encoding='ISO-8859-1') as f:
                    res, voc = Dot2Sequence(f)
                    for i in res:
                        file1 = open(path +"\\" + os.path.splitext(filename)[0] + ".txt", 'a')
                        file1.write(str(i) + '\n')
                        file1.close()
                    f.close()
                if num == 5000:
                    break
                else:
                    num = num+1
                # voc_all = list(set(voc_all + voc))
    # print(len(voc_all))
    # for i in voc_all:
    #     f = open(outputPath2, 'a')
    #     f.write(str(i)+'\n')
    #     f.close()