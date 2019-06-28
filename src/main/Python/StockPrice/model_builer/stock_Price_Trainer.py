#!/usr/bin/env python
# coding: utf-8

# In[113]:


import numpy as np
import pandas as pd
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import cross_val_score,cross_validate,train_test_split
from sklearn.preprocessing import StandardScaler,Normalizer, RobustScaler
from sklearn.metrics import *
# import seaborn as sns
# from sklearn.discriminant_analysis import LinearDiscriminantAnalysis as LDA
import sys
import pickle
import os

a = sys.stdin.read()
if a==" " or a=="":
    sys.exit()

# print("input is  : ", a)

data_path,model_path = a.split()

# In[115]:

# print("Inside python file\n")
df = pd.read_csv(data_path,thousands=',')


# In[116]:


df.head()


# In[117]:


train_set = df.drop('date',axis =1)


# In[118]:


train_set.head()


# In[119]:


y_train = train_set['open']
train_set.drop('open',axis=1,inplace=True)


# In[120]:


x_train,x_test,y_train,y_test = train_test_split(train_set,y_train,test_size=0.2)


# In[121]:


# sns.pairplot(train_set,palette='bright')


# In[122]:


linear_reg = LinearRegression(normalize=True)
linear_reg.fit(x_train,y_train)


# In[123]:


pred = linear_reg.predict(x_test)


# In[124]:


error_mae = mean_absolute_error(pred,y_test)
error_rmse = mean_squared_error(pred,y_test)
accuracy = r2_score(pred,y_test)*100



print("error rmse:",error_rmse,"error mae:",error_mae,"R2 scrore accuracy: ",accuracy)

path = model_path

file = open(path,'wb')
pickle.dump(linear_reg,file)
file.close()

try :
    sys.stdout.write(model_path)
except Exception as e:
    print("path not returned as ",e)

