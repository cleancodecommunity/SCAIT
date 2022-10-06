#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Aug  3 20:24:40 2022

@author: farshad.toosi
"""
import ast

class callEntity:
    
    def __init__(self, node):
        
        self.components = self.__getMethodCall( node)
        self.isTarget = False
        self.target = None
        self.lineNum = -1
        
        
    def __getMethodCall(self, node):
        #aa.bb.func(a, v, b, s=None)
        
        pref = []
        name = ""
        methodCallDetails = []
        components = {}
        for subNode in ast.walk(ast.parse(node.func)):
            if isinstance(subNode , ast.Name):
                pref.append(subNode.id)
                name = subNode.id
            if isinstance(subNode , ast.Attribute):
                pref.append(subNode.attr)
                
        if len(pref) > 0:
            #methodCallDetails.append({"funcName":pref[0] })
            components["funcName"] = pref[0]
            del pref[0]
            #methodCallDetails.append({"pref": pref})
            components["pref"] = pref
        else:
            #methodCallDetails.append({"funcName":name })
            components["funcName"] = name
        
        
        
                
        allArgs = []
        for subNode in node.args:
            prefArguments = []
            name = ""
            singleArg = {}
            for subsubNode in ast.walk(ast.parse(subNode)):
                if isinstance(subsubNode , ast.Name):
                    name = subsubNode.id
                    prefArguments.append(subsubNode.id)
                if isinstance(subsubNode , ast.Attribute):
                    prefArguments.append(subsubNode.attr)
            if len(prefArguments) >0:
                #methodCallDetails.append({"argName":prefArguments[0]})
                singleArg["argName"] = prefArguments[0]
                del prefArguments[0]
                #methodCallDetails.append({"prefArg": prefArguments})
                singleArg["prefArg"] = prefArguments
            else:
                #methodCallDetails.append({"argName":name})
                singleArg["argName"] = name
            allArgs.append(singleArg)
        components["args"] = allArgs
        return components
        """
        not applicable for method call. Method call does not have keyword atg.
        for subNode in node.keywords:
            for subsubNode in ast.walk(ast.parse(subNode)):
                if isinstance(subsubNode , ast.keyword):
                    print("Call: KeyWords name\tValue:\t",subsubNode.arg)
                if isinstance(subsubNode , ast.Constant):
                    print("Call: Keywords value\tValue:\t",subsubNode.value)
          """          
            
    def getComponents(self):
        return self.components
    
    def setTarget(self, target):
        self.target = target    
        self.isTarget = True
    
    def hasTarget(self):
        return self.isTarget
    
    def getTarget(self):
        return self.target
    
    def setLineNum(self, lNum):
        self.lineNum = lNum
        
    def getLineNum(self):
        return self.lineNum

    