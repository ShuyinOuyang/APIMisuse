#!/usr/bin/env python
# -*- coding:utf-8 -*-
# @Time  : 06/03/2020 22:36
# @Author: ouyangshuyin
# @File  : filter_code.py
# @Description: filter the target code with "javax.crypto" inside
import os
import string
import shutil

inputPath = "F:\\data\\mubench-md-study-data\\mubench-md-study-data-2019-01\\mubench-md-study-data-2019-01~\\Users\\svenamann\\MUBench\\checkouts-xp\\checkouts"
inputPath2 = "F:\\test"
outputPath = "F:\\train"

inputPath3 = "D:\\A_Document\\Adapter_project\\data"
outputPath3 = "D:\\A_Document\\Adapter_project\\train"

def get_fileList(dir):
    i=0
    for home, dirs, files in os.walk(dir):
        for filename in files:
            if os.path.splitext(filename)[1] == ".java" and not ("._" in os.path.splitext(filename)[0]):
                print(os.path.join(home, filename))
                with open(os.path.join(home, filename), 'r', encoding='ISO-8859-1') as f:
                    if(f.read().find("javax.crypto")!= -1):
                        shutil.copy(os.path.join(home, filename), outputPath3+"\\"+str(i)+".java")
                        print(i)
                        i=i+1
                    f.close()

if __name__ == "__main__":
    Filelist = get_fileList(inputPath3)
    print(1)