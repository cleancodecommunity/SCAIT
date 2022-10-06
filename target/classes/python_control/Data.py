
"""
Created on Fri Jul 29 20:21:12 2022

@author: farshad.toosi
"""


class Data:
    
   
    
    def __init__(self):
        self.classes = []
        self.methods = []
        self.variables = []
    
    def addClass(self, newClass):
        self.classes.append(newClass)
        
    def addMethod(self, newMethod):
        self.methods.append(newMethod)
    
    
    def getClasses(self):
        return self.classes
    
    def getMethods(self):
        return self.methods
    
    