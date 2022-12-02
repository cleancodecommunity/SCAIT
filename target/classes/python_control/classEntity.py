#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Jul 29 14:37:33 2022

@author: farshad.toosi
"""


class classEntity():
    
    
    def __init__(self, name, fullName, project ):
        self.name = name
        self.fullName = fullName
        self.project = project
        self.project.getClasses().append(self)
        self.containedMethods = []
        self.containedVariables = []
        self.packageName = None
        self.obs = False # whether it is overriden
        self.Callees = []
        self.assign = []
        self.lineNum = -1
        self.incomingMethods = []
        self.outgoingMethods = []
    
    def getName(self):
        return self.name
    
    def getFullName(self):
        return self.fullName
    
    def setPackageName(self, name):
        self.packageName = name
        
    def getPackageName(self):
        return self.packageName
    
    
    def addMethod(self, method):
        self.containedMethods.append(method)
        
    def getMethods(self):
        return self.containedMethods
    
    def addVariables(self, variable):
        self.containedVariables.append(variable)
        
    def getVariables(self):
        return self.containedVariables
    
    def setObs(self, stat):
        self.obs = stat
        
    def getObs(self):
        return self.obs
    
    def addCallee(self, callee):
        self.Callees.append(callee)
    
    def setCallee(self, callees):
        self.Callees.extend(callees)
    
    def getCalleess(self):
        return self.Callees
    
    def getAssign(self):
        return self.assign
    
    def addAssign(self, assg):
        self.assign.append(assg)
        
    def setLineNum(self, lNum):
        self.lineNum = lNum
        
    def getLineNum(self):
        return self.lineNum
    
    def addIncomingMethod(self, mth):
        self.incomingMethods.append(mth)
        
    def addOutgoingMethod(self, mth):
        self.outgoingMethods.append(mth)
        
    def getIncomings(self):
        return self.incomingMethods
    
    def getOutgoings(self):
        return self.outgoingMethods
    
    