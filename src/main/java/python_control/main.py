#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Jul 22 20:14:08 2022

@author: Farshad Ghassemi Toosi
"""
import ast, itertools
import re, importlib

import pickle

import Data
import classEntity
import methodEntity
import inspect
import assignEntity
import callEntity

import json 
#from objectConvertor import convert 
from objectConvertor1 import convert 


fileName ='/Users/farshad.toosi/eclipse-workspace/SCAIT/src/main/java/python_control/pickle.py'

data = ""
with open(fileName, 'r') as file:
    data = file.read()


Root = "?Root"


#print(ast.dump (ast.parse(data)))



def getFunctionDef(node, claz, project):
    
    
    allArgs = None
    returnStmt = None
    callees = []
    assgs = []
    for subNode in ast.walk(ast.parse(node)):
        if isinstance(subNode, ast.Return):
            returnStmt = getReturn(subNode)
        if isinstance(subNode, ast.arguments):
            allArgs = getParametersNormal(subNode)
        
            
        if isinstance(subNode, ast.Assign):
            left = subNode.targets[0]
            if isinstance(subNode.value, ast.Call):
                newCall = callEntity.callEntity(subNode.value)
                newCall.setTarget(left)
                callees.append(newCall)
            
            
        if isinstance(subNode, ast.Expr):
            if isinstance(subNode.value, ast.Call):
                newCall = callEntity.callEntity(subNode.value)
                callees.append(newCall)
    
    method = methodEntity.methodEntity(node.name, allArgs, project, isStatic(node),claz)
    method.setReturn(returnStmt)
    method.setCallee(callees)
    method.setLineNum(node.lineno)
            
        
    
    

def getReturn(node):
    returnStmt = None
    for subNode in ast.walk(ast.parse(node)):
        if isinstance(subNode, ast.Constant):
            returnStmt = str(type(subNode.value))+"-"+str(subNode.value)
        if isinstance(subNode, ast.Name):
            returnStmt = subNode.id
    return returnStmt
    


def getParametersNormal(node): #TODO func(a, b, *args) also need to be done.
    """
    allArgs = ""
    
    for subNode in node.args:
        allArgs = allArgs +"-pos:" +str(subNode.arg)
        
    if node.vararg != None:
        allArgs = allArgs +"-arg*:" +str(node.vararg.arg)
        
    if node.kwarg != None:
        allArgs = allArgs +"-kwarg*:" +str(node.kwarg.arg)
        
    for subNode in node.defaults:
        allArgs = allArgs +"-default:" +str(subNode.value)
    
    """
    allArgs = {}
    
    for subNode in node.args:
        allArgs[str(subNode.arg)] = "-pos"
        
        
    if node.vararg != None:
        allArgs[str(node.vararg.arg)] = "-arg*"
        
        
    if node.kwarg != None:
        print("Nameeee\t",str(node.kwarg.arg))
        allArgs[str(node.kwarg.arg)] = "-kwarg*"
    
        
    for subNode in node.defaults:
        
        
        if isinstance( subNode, ast.UnaryOp):
             allArgs[str(subNode.operand.value)] = "-default"
            
        elif isinstance( subNode, ast.Name):
             allArgs[str(subNode.id)] = "-default"
        elif isinstance( subNode, ast.Call):
             
             if isinstance(subNode.func, ast.Attribute):
                 allArgs[str(subNode.func)] = "-default"
             else:
                 allArgs[str(subNode.func.id)] = "-default"
        elif isinstance(subNode, ast.Lambda):
                 
            allArgs[str(subNode.body)] = "-default"
            
        elif isinstance(subNode, ast.Tuple):
             allArgs[str(subNode)] = "-default"
            
        else:
            
            allArgs[str(subNode.value)] = "-default"
            
        #print("....",subNode.arg)
    return allArgs




        

        

    
    
    


    
def getClass(node, project):
    
    newClass = classEntity.classEntity(str(node.name), str(node.name)+"-"+fileName, project)
    callees = []
    newClass.setLineNum(node.lineno)
    for subNode in ast.walk(ast.parse(node)):
        if isinstance(subNode, ast.FunctionDef):
            
            getFunctionDef(subNode, newClass, project)
    
    
    for subNode in getClassMembers(node):
        if isinstance(subNode, ast.Assign):
                left = subNode.targets[0]
                if isinstance(subNode.value, ast.Call):
                    newCall = callEntity.callEntity(subNode.value)
                    newCall.setTarget(left)
                    callees.append(newCall)
                
                
        if isinstance(subNode, ast.Expr):
            if isinstance(subNode.value, ast.Call):
                newCall = callEntity.callEntity(subNode.value)
                callees.append(newCall)
    newClass.setCallee(callees)
    
    
   
        
    
    
    
    
def getClassMembers (node): # to get class members except functions and their belongings.
    children = []
    for child in ast.iter_child_nodes(node):
        
        if not(  isinstance(child, ast.FunctionDef)):
            getClassMembers (child)
            children.append(child)
        
    return children
        
        
def getRootMembers (node): # to get root members except classes and their belongings.
    children = []
    for child in ast.iter_child_nodes(node):
        
        if not(  isinstance(child, ast.ClassDef)):
            getRootMembers (child)
            children.append(child)
        
    return children
    
    

def isStatic(node):
    if(len(node.decorator_list)>0) and 'id' in node.decorator_list[0].__dict__:
        if node.decorator_list[0].id == "staticmethod":
                return True
    if len(node.args.args) == 0:
        return True
    else:
        return False
    return True
    

def methodCallSolver(project, callees, methodContainer):
    
    
    objects = {}
    
    # object collections
    for callObj in callees:
        call  = callObj.getComponents()
        constructor = call["funcName"]
        if callObj.hasTarget():
            for claz in project.getClasses():
                if claz.getName() == constructor and hasattr(callObj.getTarget(), 'id'): 
                    objects[callObj.getTarget().id] = constructor
        
    #print(objects)
    # non static method call within a class 
    for callObj in callees:
        call  = callObj.getComponents()
        funcName = call["funcName"]
        
        for method in  methodContainer.getClassContainer().getMethods():
            if funcName == method.getName():
                if not (method.isStatic()) and not (methodContainer.isStatic()):
                    if method.getClassContainer().getName() == methodContainer.getClassContainer().getName():
                        if len(list(methodContainer.getSigniture().keys())) > 0 and len(call['pref'])>0:
                            if call["pref"][0] == list(methodContainer.getSigniture().keys())[0]:
                                methodContainer.addOutgoingMethod(method)
                                method.addIncomingMethod(methodContainer)
                if not (method.isStatic()) and  (methodContainer.isStatic()):
                    if method.getClassContainer().getName() == methodContainer.getClassContainer().getName():
                        for obj in objects:
                            if call["pref"][0] == obj and methodContainer.getClassContainer().getName() == objects[obj]:
                                methodContainer.addOutgoingMethod(method)
                                method.addIncomingMethod(methodContainer)
                
    #  static method call within a class 
    for callObj in callees:
        call  = callObj.getComponents()
        funcName = call["funcName"]
        #print("+++",funcName, methodContainer.getName())
        
        for method in  methodContainer.getClassContainer().getMethods():
            if funcName == method.getName():
                if  (method.isStatic()) and  (methodContainer.isStatic()):
                    if method.getClassContainer().getName() == methodContainer.getClassContainer().getName():
                        if call["pref"][0] == str(methodContainer.getClassContainer().getName()):
                            methodContainer.addOutgoingMethod(method)
                            method.addIncomingMethod(methodContainer)
                            
                
                
    # static method is called by a method from a different class
    for callObj in callees:
        call  = callObj.getComponents()
        funcName = call["funcName"]
        if len(call["pref"]) == 1:
            CallerClassContainter =call["pref"][0]
            if CallerClassContainter != "self":
                for claz in project.getClasses():
                    if claz.getName() == CallerClassContainter:
                        if CallerClassContainter != methodContainer.getClassContainer().getName():
                            for method in claz.getMethods():
                                if method.getName() == funcName:
                                    methodContainer.addOutgoingMethod(method)
                                    method.addIncomingMethod(methodContainer)
                                    
                for obj in objects.keys():
                    if CallerClassContainter == obj:
                        for claz in project.getClasses():
                            if claz.getName() != methodContainer.getClassContainer().getName() and objects[CallerClassContainter] == claz.getName():
                                for method in claz.getMethods():
                                    if method.getName() == funcName:
                                        methodContainer.addOutgoingMethod(method)
                                        method.addIncomingMethod(methodContainer)
                                        
        if len(call["pref"]) == 0 :
            for claz in project.getClasses():
                    if Root == claz.getName():
                        if Root != methodContainer.getClassContainer().getName():
                            #print('ROooott', claz.getName(), methodContainer.getName())
                            for method in claz.getMethods():
                                if method.getName() == funcName:
                                    methodContainer.addOutgoingMethod(method)
                                    method.addIncomingMethod(methodContainer)
                                    
                        
    
    
def classCallSolver(project, callees, classContainer):
    
    objects = {}
    
    # object collections
    for callObj in callees:
        call  = callObj.getComponents()
        constructor = call["funcName"]
        if callObj.hasTarget():
            for claz in project.getClasses():
                if claz.getName() == constructor: 
                    objects[callObj.getTarget().id] = constructor
    
    
        # static method is called by a different class
    for callObj in callees:
        call  = callObj.getComponents()
        funcName = call["funcName"]
        
        if len(call["pref"]) == 1:
            ObjectOrClassName =call["pref"][0]
            if ObjectOrClassName != "self":
                for obj in objects.keys():
                    if ObjectOrClassName == obj:
                        for claz in project.getClasses():
                            if objects[obj] == claz.getName():
                                for claz in project.getClasses():
                                    if objects[obj] == claz.getName():
                                        for method in claz.getMethods():
                                            if method.getName() == funcName:
                                                classContainer.addOutgoingMethod(method)
                                                
                for claz in project.getClasses():
                    if ObjectOrClassName == claz.getName():
                        if ObjectOrClassName != classContainer.getName():
                            for method in claz.getMethods():
                                if method.getName() == funcName:
                                    classContainer.addOutgoingMethod(method)
                                    
    
    #  static method call within a class 
    for callObj in callees:
        call  = callObj.getComponents()
        funcName = call["funcName"]
        for method in  classContainer.getMethods():
            if funcName == method.getName():
                if  (method.isStatic()):                    
                    if method.getClassContainer().getName() == classContainer.getName():
                        if len(call["pref"]) == 0:                            
                            classContainer.addOutgoingMethod(method)
                            
def getRoot(node, claz, project):
    
    callees = []

    

    for subNode in ast.walk(ast.parse(node)):
        
        if isinstance(subNode, ast.FunctionDef):
            getFunctionDef(subNode, claz, project)
        
    
    if isinstance(node, ast.Assign):                
        left = node.targets[0]
        if isinstance(node.value, ast.Call):
                newCall = callEntity.callEntity(node.value)
                newCall.setTarget(left)
                callees.append(newCall)
                
                
    if isinstance(node, ast.Expr):
        
        if isinstance(node.value, ast.Call):
            
            newCall = callEntity.callEntity(node.value)
            
            callees.append(newCall)
            #print(newCall.getComponents())
            #claz.addOutgoingMethod(self, mth):

    
    claz.setCallee(callees)
    
    
   
    #print("----",claz.getCalleess())
    

def main():
    project = Data.Data()
    
    Roote = classEntity.classEntity(Root, str(Root)+"-"+fileName, project)
    
    
    for node in ast.walk(ast.parse(data)):
        if(isinstance(node, ast.ClassDef)):
            getClass(node, project)
    
    print("***************")
    for nn in getRootMembers(ast.parse(data)):
        getRoot(nn, Roote,  project)
    """
    for c1 in project.getClasses():
        for c2 in c1.getCalleess():
            print(c1.getName(), c2.getComponents())
            """
    print("********Calls*******")
    for mt in project.getMethods():
        methodCallSolver(project, mt.getCalleess(), mt)
        
    for cl in project.getClasses():
        classCallSolver(project, cl.getCalleess(), cl)
        
    print("***************")
    print("-----")
    

    if False:
        for met in project.getClasses():
            print(met.getName())
            
            for outs in met.getOutgoings():
                #print("-----",outs.getName())
                #print("-----",outs.getClassContainer().getName())
                print("-----", outs)
    
    
    
    
    kk = convert(project)
    print(kk.getMembers())
    result = open("ObjectPython.txt", "w")
    result.write(str(kk.getMembers()))
    result.close()

    
# Serializing json  
#json_object = json.dumps(dictionary, indent = 4) 
#print(json_object)
        
main()
