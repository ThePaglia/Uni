from scipy.optimize import linprog

def simplex_method(c, A, b):
    # Risolve il problema di programmazione lineare:
    # min c^T x
    # soggetto a: Ax <= b e x >= 0
    res = linprog(c, A_ub=A, b_ub=b, method='highs')

    if res.success:
        return res.x
    else:
        return 'L\'algoritmo del simplesso non ha trovato una soluzione ottima.'

# Esempio di utilizzo:
c = [-200, -300]            # min z = -200x1 - 300x2
A = [[1, 2], [3, 1]]        # 1x1 + 2 x2 <= 80
b = [80, 90]                # 3x1 + 1x2 <= 90
print(simplex_method(c, A, b))
