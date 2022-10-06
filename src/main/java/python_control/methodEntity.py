#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Jul 29 14:49:21 2022

@author: farshad.toosi
"""
#import classEntity


class methodEntity:
    
    
    def __init__(self, name, signiture, project, static, classContainer="Root"):
        self.name = name
        self.signiture = signiture
        self.classContainer = classContainer
        
        self.classContainer.addMethod(self)
        self.project = project
        self.project.getMethods().append(self)
        self.returnStmt = None
        self.static = static
        self.Callees = []
        self.incomingMethods = []
        self.outgoingMethods = []
        self.obs = False
        self.assign = []
        self.lineNum = -1
        self.selph =None
        
        
    
        
    def getName(self):
        return self.name
        
    def setName(self, name):
        self.name = name
        
    def getClassContainer(self):
        
        return self.classContainer
        
    def getSigniture(self):
        return self.signiture
        
    def setReturn(self, returnStm):
        self.returnStmt = returnStm
    
    def getReturn(self):
        return self.returnStmt
    
    def isStatic(self):
        return self.static
    
    def addCallee(self, callee):
        self.Callees.append(callee)
    
    def setCallee(self, callees):
        self.Callees = callees
    
    def getCalleess(self):
        return self.Callees
    
    def addIncomingMethod(self, mth):
        self.incomingMethods.append(mth)
        
    def addOutgoingMethod(self, mth):
        self.outgoingMethods.append(mth)
        
    def getIncomings(self):
        return self.incomingMethods
    
    def getOutgoings(self):
        return self.outgoingMethods
    
    def setObs(self, stat):
        self.obs = stat
        
    def getObs(self):
        return self.obs
    
    def getAssign(self):
        return self.assign
    
    def addAssign(self, assg):
        self.assign.append(assg)
        
    def setAssigns(self, assgs):
        self.assign = assgs
        
    def setLineNum(self, lNum):
        self.lineNum = lNum
        
    def getLineNum(self):
        return self.lineNum
    
    def setSelf(self, selph):
        self.selph = selph
        
    def getSelf(self):
        return self.selph
    
    