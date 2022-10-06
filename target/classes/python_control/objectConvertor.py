#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Aug 11 21:24:35 2022

@author: farshad.toosi
"""



class convert:
    
    heirarchy = {}
    heir = 0
    
    def __init__(self, obj, dic):
        self.obj = obj 
        self.dic = dic
        
        print(" ",type(obj),"  :  {")
        
        print("code : ",hash(obj)," ,")
        convert.heirarchy[hash(obj)] = convert.heir 
        convert.heir += 1
        
    def __getAllFilds(self):
        
        return vars(self.obj)
    
    def getAllMethods(self):
        return help(self.obj)
    
    
                
    def checkCollectionMembers(self, data):
        if isinstance(data, list):
            if len(data) > 0:
                print("{ list: [")
                self.checkMembers(2, data)
                print("] } ,")
        if isinstance(data, tuple):
            if len(data) > 0:
                print("{ tuple: [")
                self.checkMembers(2, data)
                print("] } ,")
        if isinstance(data, set):
            if len(data) > 0:
                print("{ set: [")
                self.checkMembers(2, data)
                print("] } ,")
        if isinstance(data, dict):
            if len(data) > 0:
                print("{ dict keys: [")
                self.checkMembers(2, list(data.keys()))
                print("] } ,")
                print("{ dict values: [")
                self.checkMembers(2, list(data.values()))
                print("] } ,")
                
        
    def checkMembers(self, mode, data):
        if mode == 1:
            members = self.__getAllFilds()
            for mem in members:
                if isinstance(members[mem], int):
                    print(mem," : ", members[mem]," , ")
                elif isinstance(members[mem], str):
                    print(mem," : ", members[mem]," , ")
                elif isinstance(members[mem], bool):
                    print(mem," : ", members[mem]," , ")
                elif isinstance(members[mem], float):
                    print(mem," : ", members[mem]," , ")
                elif members[mem] is None:
                    print(mem," : ", members[mem]," , ")
                    
                elif isinstance(members[mem], list) or isinstance(members[mem], set) or isinstance(members[mem], tuple)  or isinstance(members[mem], dict):
                    if len(members[mem])>0 :
                        print(mem," : ")
                        self.checkCollectionMembers(members[mem])
                else:
                    if (hash(members[mem]) in convert.heirarchy.keys() ):
                        print(mem, members[mem], " : ", hash(members[mem]), " ,")
                    if not(hash(members[mem]) in convert.heirarchy.keys() ):
                        newObject = convert(members[mem], self.fJson)
                        newObject.checkMembers(1,"")
            print("} ,")
        else:
            members = data
            for mem in members:
                if isinstance(mem, int):
                    print(mem, " , ")
                elif isinstance(mem, str):
                    print(mem, " , ")
                elif isinstance(mem, bool):
                    print(mem, " , ")
                elif isinstance(mem, float):
                    print(mem, " , ")
                elif mem is None:
                    print(mem)
                elif isinstance(mem, list) or isinstance(mem, set) or isinstance(mem, tuple)  or isinstance(mem, dict):
                    print(mem," : ")
                    self.checkCollectionMembers(mem)
                else:
                    if (hash(mem) in convert.heirarchy.keys() ):
                        print(mem, " : ", hash(mem)," , ")
                    if not(hash(mem) in convert.heirarchy.keys() ):
                        newObject = convert(mem, self.fJson)
                        newObject.checkMembers(1,"")
        
                
                
                
                
                