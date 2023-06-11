import numpy as np
import scipy.linalg as spLin
import RisolviSis


def solve_nsis(A, B):
    # Test dimensione
    m, n = A.shape
    flag = 0
    if n != m:
        print("Matrice non quadrata")

        return

    X = np.zeros((n, n))
    PT, L, U = spLin.lu(A)
    P = PT.T.copy()
    if flag == 0:
        for i in range(n):
            y, flag = RisolviSis.Lsolve(L, np.dot(P, B[:, i]))

            x, flag = RisolviSis.Usolve(U, y)
            X[:, i] = x.reshape(
                n,
            )
    else:
        print("Elemento diagonale nullo")
        X = []
    return X


def jacobi(A, b, x0, toll, it_max):
    errore = 1000
    d = np.diag(A)
    n = A.shape[0]
    invM = np.diag(1 / d)
    E = np.tril(A, -1)
    F = np.triu(A, 1)
    N = -(E + F)
    T = np.dot(invM, N)
    autovalori = np.linalg.eigvals(T)
    raggiospettrale = np.max(np.abs(autovalori))
    print("raggio spettrale jacobi", raggiospettrale)
    it = 0
    # xold=x0.copy()
    er_vet = []
    while it <= it_max and errore >= toll:
        x = (b + np.dot(N, x0)) / d.reshape(n, 1)
        errore = np.linalg.norm(x - x0) / np.linalg.norm(x)
        er_vet.append(errore)
        x0 = x.copy()
        it = it + 1
    return x, it, er_vet


def gauss_seidel(A, b, x0, toll, it_max):
    errore = 1000
    d = np.diag(A)
    D = np.diag(d)
    E = np.tril(A, -1)
    F = np.triu(A, 1)
    M = D + E
    N = -F
    T = np.dot(np.linalg.inv(M), N)
    autovalori = np.linalg.eigvals(T)
    raggiospettrale = np.max(np.abs(autovalori))
    print("raggio spettrale Gauss-Seidel ", raggiospettrale)
    it = 0
    er_vet = []
    while it <= it_max and errore >= toll:
        temp = b - np.dot(F, x0)
        x, flag = RisolviSis.Lsolve(M, temp)
        errore = np.linalg.norm(x - x0) / np.linalg.norm(x)
        er_vet.append(errore)
        x0 = x.copy()
        it = it + 1
    return x, it, er_vet


def gauss_seidel_sor(A, b, x0, toll, it_max, omega):
    errore = 1000
    d = np.diag(A)
    D = np.diag(d)
    Dinv = np.diag(1 / d)
    E = np.tril(A, -1)
    F = np.triu(A, 1)
    Momega = D + omega * E
    Nomega = (1 - omega) * D - omega * F
    T = np.dot(np.linalg.inv(Momega), Nomega)
    autovalori = np.linalg.eigvals(T)
    raggiospettrale = np.max(np.abs(autovalori))
    print("raggio spettrale Gauss-Seidel SOR ", raggiospettrale)

    M = D + E
    N = -F
    it = 0
    xold = x0.copy()
    xnew = x0.copy()
    er_vet = []
    while it <= it_max and errore >= toll:
        temp = b - np.dot(F, xold)
        xtilde, flag = Lsolve(M, temp)
        xnew = (1 - omega) * xold + omega * xtilde
        errore = np.linalg.norm(xnew - xold) / np.linalg.norm(xnew)
        er_vet.append(errore)
        xold = xnew.copy()
        it = it + 1
    return xnew, it, er_vet


def steepestdescent(A, b, x0, itmax, tol):
    # Metodo del gradiente   per la soluzione di un sistema lineare con matrice dei coefficienti simmetrica e definita positiva
    n, m = A.shape
    if n != m:
        print("Matrice non quadrata")
        return [], []

    # inizializzare le variabili necessarie
    x = x0
    r = A.dot(x) - b
    p = -r
    it = 0
    nb = np.linalg.norm(b)
    errore = np.linalg.norm(r) / nb
    vec_sol = []
    vec_sol.append(x)
    vet_r = []
    vet_r.append(errore)

    # utilizzare il metodo del gradiente per trovare la soluzione
    while errore >= tol and it < itmax:
        it = it + 1
        Ap = A.dot(p)
        rTr = np.dot(r.T, r)
        alpha = rTr / np.dot(p.T, Ap)

        x = x + alpha * p
        vec_sol.append(x)
        r = r + alpha * Ap
        errore = np.linalg.norm(r) / nb
        vet_r.append(errore)
        p = -r

    return x, vet_r, vec_sol, it


def conjugate_gradient(A, b, x0, itmax, tol):
    # Metodo del gradiente coniugato per la soluzione di un sistema lineare con matrice dei coefficienti simmetrica e definita positiva
    n, m = A.shape
    if n != m:
        print("Matrice non quadrata")
        return [], []

    # inizializzare le variabili necessarie
    x = x0
    r = A.dot(x) - b
    p = -r
    it = 0
    nb = np.linalg.norm(b)
    errore = np.linalg.norm(r) / nb
    vec_sol = []
    vec_sol.append(x0)
    vet_r = []
    vet_r.append(errore)
    # utilizzare il metodo del gradiente coniugato per trovare la soluzione
    while errore >= tol and it < itmax:
        it = it + 1
        Ap = A.dot(p)
        rtr = np.dot(r.T, r)
        alpha = rtr / np.dot(p.T, Ap)
        x = x + alpha * p
        vec_sol.append(x)
        r = r + alpha * Ap
        gamma = np.dot(r.T, r) / rtr
        errore = np.linalg.norm(r) / nb
        vet_r.append(errore)
        p = -r + gamma * p

    return x, vet_r, vec_sol, it


def eqnorm(A, b):
    # Soluzione di un sistema sovradeterminato facendo uso delle equazioni normali
    G = A.T @ A

    print("Indice di condizionamento di G ", np.linalg.cond(G))
    f = A.T @ b
    L = spLin.cholesky(G, lower=True)

    y, flag = RisolviSis.Lsolve(L, f)
    if flag == 0:
        x, flag = RisolviSis.Usolve(L.T, y)

    return x


# Soluzione di un sistema sovradeterminato facendo uso della fattorizzazione QR
def qrLS(A, b):
    n = A.shape[1]  # numero di colonne di A
    Q, R = spLin.qr(A)
    h = Q.T @ b
    x, flag = RisolviSis.Usolve(R[0:n, :], h[0:n])
    residuo = np.linalg.norm(h[n:]) ** 2
    return x, residuo


# Soluzione di un sistema sovradeterminato facendo uso della fattorizzazione SVD
def SVDLS(A, b):
    n = A.shape[1]  # numero di colonne di A
    U, s, VT = spLin.svd(A)  # Attenzione : Restituisce U, Sigma e VT=VTrasposta)
    V = VT.T
    thresh = (
        np.spacing(1) * m * s[0]
    )  ##Calcolo del rango della matrice, numero dei valori singolari maggiori di una soglia
    k = np.count_nonzero(s > thresh)
    print("rango=", k)
    d = U.T @ b
    d1 = d[:k].reshape(k, 1)
    s1 = s[:k].reshape(k, 1)
    # Risolve il sistema diagonale di dimensione kxk avene come matrice dei coefficienti la matrice Sigma
    c = d1 / s1
    x = V[:, :k] @ c
    residuo = np.linalg.norm(d[k:]) ** 2
    return x, residuo


# Funzioni per la costruzione del polinomio interpolatore nella base di
# Lagrange


def plagr(xnodi, k):
    """
    Restituisce i coefficienti del k-esimo pol di
    Lagrange associato ai punti del vettore xnodi
    """
    xzeri = np.zeros_like(xnodi)
    n = xnodi.size
    if k == 0:
        xzeri = xnodi[1:n]
    else:
        xzeri = np.append(xnodi[0:k], xnodi[k + 1 : n])

    num = np.poly(xzeri)
    den = np.polyval(num, xnodi[k])

    p = num / den

    return p


def InterpL(x, f, xx):
    """ "
    %funzione che determina in un insieme di punti il valore del polinomio
    %interpolante ottenuto dalla formula di Lagrange.
    % DATI INPUT
    %  x  vettore con i nodi dell'interpolazione
    %  f  vettore con i valori dei nodi
    %  xx vettore con i punti in cui si vuole calcolare il polinomio
    % DATI OUTPUT
    %  y vettore contenente i valori assunti dal polinomio interpolante
    %
    """
    n = x.size
    m = xx.size
    L = np.zeros((m, n))
    for k in range(n):
        p = plagr(x, k)
        L[:, k] = np.polyval(p, xx)

    return np.dot(L, f)
