import sympy as sym
import numpy as np
import matplotlib.pyplot as plt
import numpy.linalg as npl
import scipy.linalg as spl
import math

def row_echelon_form(matrix):
    n_rows, n_cols = len(matrix), len(matrix[0])
    row_idx = 0
    
    for col_idx in range(n_cols):
        # Scambia la riga corrente con una riga che ha il primo elemento non nullo
        if matrix[row_idx][col_idx] == 0:
            for i in range(row_idx+1, n_rows):
                if matrix[i][col_idx] != 0:
                    matrix[row_idx], matrix[i] = matrix[i], matrix[row_idx]
                    break
            else:
                continue
        
        # Rendi zero gli elementi sotto il pivot nella colonna corrente
        pivot = matrix[row_idx][col_idx]
        for i in range(row_idx+1, n_rows):
            multiplier = -matrix[i][col_idx] / pivot
            for j in range(col_idx, n_cols):
                matrix[i][j] += multiplier * matrix[row_idx][j]
        
        # Passa alla prossima riga
        row_idx += 1
        
        # Se tutte le righe hanno il primo elemento nullo, termina
        if row_idx == n_rows:
            break
    
    return matrix

def row_echelon(matrix):
    lead = 0
    rowCount = len(matrix)
    columnCount = len(matrix[0])
    for r in range(rowCount):
        if lead >= columnCount:
            return matrix
        i = r
        while matrix[i][lead] == 0:
            i += 1
            if i == rowCount:
                i = r
                lead += 1
                if columnCount == lead:
                    return matrix
        matrix[i], matrix[r] = matrix[r], matrix[i]
        lv = matrix[r][lead]
        matrix[r] = [mrx / float(lv) for mrx in matrix[r]]
        for i in range(rowCount):
            if i != r:
                lv = matrix[i][lead]
                matrix[i] = [iv - lv*rv for rv, iv in zip(matrix[r], matrix[i])]
        lead += 1
    return matrix


testA = np.array([[1,-1,3],[0,1,12],[0,0,-59]])
print(row_echelon_form(testA))
print()
print(row_echelon(testA))
print()
print(sym.Matrix(testA).rref())
print()
print(sym.Matrix(testA).echelon_form(with_pivots=True))
print()
print(sym.Matrix(testA).rank())