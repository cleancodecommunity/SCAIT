#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Aug 20 22:19:18 2022

@author: farshad.toosi
"""
import json

class convert:
    
    heirarchy = {}
    heir = 0
    
    def __init__(self, obj):
        self.obj = obj 
        self.dic = {}
        
        
        
        
    def __getAllFilds(self, obj):
        
        return vars(obj)
    
    def getAllMethods(self):
        return help(self.obj)
    


    def getMembers(self):
        
      
        if not (isinstance(self.obj, list)) and not (isinstance(self.obj, set)) and not (isinstance(self.obj, tuple)) and not (isinstance(self.obj, dict)):
            if  hash(self.obj) not in  convert.heirarchy:
                convert.heirarchy[hash(self.obj)] = convert.heir 
                convert.heir += 1
                self.dic["?Code"] = hash(self.obj)
                self.dic["?Name"] = str(type(self.obj)) + " False"
                
                members = self.__getAllFilds(self.obj)
                
                for mem in members:
                    if (isinstance(members[mem], int)) or (isinstance(members[mem], float)) or (isinstance(members[mem], str)) or (isinstance(members[mem], bool)) or (members[mem] == None):
                        self.dic[mem] = members[mem]
                    elif (isinstance(members[mem], list)) or (isinstance(members[mem], set)) or (isinstance(members[mem], tuple)):
                        self.dic[mem] = self.getCollections(members[mem], 1) 
                    elif (isinstance(members[mem], dict)):
                        self.dic[mem] = self.getCollections(members[mem], 2)
                    else:
                        
                        new_obj = convert(members[mem])
                        self.dic[mem] = new_obj.getMembers()
                        
                
            else:
                self.dic["?Code"] = hash(self.obj)
                self.dic["?Name"] = str(type(self.obj)) + " True"
                
            
        return self.dic
                    
        
                
                        
                        
                    
                        
            
    def getCollections(self, obj, mode):
        if mode == 1:
            myList = []
            for mem in obj:
                if (isinstance(mem, int)) or (isinstance(mem, float)) or (isinstance(mem, str)) or (isinstance(mem, bool)) or mem == None:
                    myList.append(mem)
                elif (isinstance(mem, list)) or (isinstance(mem, set)) or (isinstance(mem, tuple)):
                    myList.append(self.getCollections( obj[mem], 1))
                else:
                    new_obj = convert(mem)
                    myList.append(new_obj.getMembers())
            return myList
        else:
            myDict = {}
            for mem in obj:
                if (isinstance(obj[mem], int)) or (isinstance(obj[mem], float)) or (isinstance(obj[mem], str)) or (isinstance(obj[mem], bool)) or mem == None:
                    myDict[mem] = obj[mem]
                else:
                    new_obj = convert(obj[mem])
                    myDict[mem] = new_obj.getMembers()
            return myDict
                
            
   
        
        