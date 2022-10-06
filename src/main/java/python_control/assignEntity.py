#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Aug  3 20:06:12 2022

@author: farshad.toosi
"""


class AssignEntity:
    
    def __init__(self, left, right):
        self.left = left
        self.right = right
        self.rightCall = False
        self.lineNum = -1
        
    def getLeft(self):
        return self.left
    
    def getRight(self):
        return self.right
    
    def isRightCall(self):
        return self.rightCall
    
    def setRightCall(self, rightcall):
        self.rightCall = rightcall
        
    def setLineNum(self, lNum):
        self.lineNum = lNum
        
    def getLineNum(self):
        return self.lineNum
    
    