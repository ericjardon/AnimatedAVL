package sample;

import static java.lang.Integer.max;
import static java.lang.Math.abs;

public class AVL<T extends Comparable<T>> {

    private Nodo<T> raiz;

    public Nodo<T> getRaiz() {
        return raiz;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    // ----------------------INSERT & REMOVE ----------------------
    public void insertar(T elemento) {
        raiz = insertarRec(elemento, raiz);
    }
    public Nodo<T> insertarRec(T elemento, Nodo<T> nodoPadre) {
        if (nodoPadre == null) {
            return new Nodo<>(elemento);
        }else if(elemento.compareTo(nodoPadre.getElemento()) < 0) {       // elemento es menor a la raíz. Subárbol izquierdo
                nodoPadre.setIzquierdo(insertarRec(elemento,nodoPadre.getIzquierdo()));
            if (abs( altura(nodoPadre.getIzquierdo())- altura(nodoPadre.getDerecho()) ) == 2) {
                if (elemento.compareTo(nodoPadre.getIzquierdo().getElemento()) < 0){     // checa si es menor a raiz y menor a sub izquierdo
                    nodoPadre = rotarSimpleDerecha(nodoPadre);
                } else {
                    nodoPadre = rotarDobleDerecha(nodoPadre);
                }
            }
        } else if (elemento.compareTo(nodoPadre.getElemento()) > 0) {        // elemento es mayor a la raíz. Subárbol derecho
                nodoPadre.setDerecho(insertarRec(elemento, nodoPadre.getDerecho()));
                if (abs( altura(nodoPadre.getIzquierdo()) - altura(nodoPadre.getDerecho()) ) == 2) {
                    if (elemento.compareTo(nodoPadre.getDerecho().getElemento()) > 0) {  // checa si es mayor a raiz y mayor a sub derecho
                        nodoPadre = rotarSimpleIzquierda(nodoPadre);
                    } else {
                        nodoPadre = rotarDobleIzquierda(nodoPadre);
                    }
                }
        }
        int alturaActual = Integer.max(altura(nodoPadre.getIzquierdo()), altura(nodoPadre.getDerecho()));
        nodoPadre.setAltura(alturaActual + 1);
        return nodoPadre;
    }

    public void remove(T e){
        raiz = removeRec(raiz, e);
    }
    private Nodo<T> removeRec(Nodo<T> nodoPadre, T e) {
        System.out.println("Llamada recursiva. nodoPadre = " + nodoPadre
                + ", A eliminar: " +e);
        if (nodoPadre == null) {
            System.out.println("nodo vacío. regresar null");
            return nodoPadre;
        } else {
            if (e.compareTo(nodoPadre.getElemento()) < 0) {
                System.out.println("Explora izquierda");
                nodoPadre.setIzquierdo(removeRec(nodoPadre.getIzquierdo(), e)); // actualizar Alturas
                if (isUnbalanced(nodoPadre)) {
                    System.out.println("Redujo arbol izquierdo. Rotar a la izquierda");
                    nodoPadre = rotarSimpleIzquierda(nodoPadre);
                }
            }
            if (e.compareTo(nodoPadre.getElemento()) > 0) {
                System.out.println("Explora derecha");
                nodoPadre.setDerecho(removeRec(nodoPadre.getDerecho(), e));     // actualizar Alturas
                if (isUnbalanced(nodoPadre)) {
                    System.out.println("Restó a árbol derecho. Rotar a la derecha");
                    nodoPadre = rotarSimpleDerecha(nodoPadre);
                }
            }
            else if (e.compareTo(nodoPadre.getElemento()) == 0) {
                System.out.println("Encontramos nodo a eliminar");
                if (nodoPadre.isLeaf()) {
                    System.out.println("Nodo a eliminar es hoja. Regresar null");
                    return null;
                } else {
                    // si tiene un solo subárbol
                    if (nodoPadre.getIzquierdo() == null) {
                        System.out.println("Sube subárbol derecho");
                        return nodoPadre.getDerecho();
                    } else if (nodoPadre.getDerecho() == null) {
                        System.out.println("Sube subárbol izquierdo");
                        return nodoPadre.getIzquierdo();
                    }
                }
                // tiene dos subárboles. Sube el más valor chico del lado derecho y borramos hoja
                System.out.println("Sustituir (" + nodoPadre.getElemento() + ") por " + findMin(nodoPadre.getDerecho()));
                nodoPadre.setElemento(findMin(nodoPadre.getDerecho()));
                System.out.println("Borrar hoja con valor duplicado");
                nodoPadre.setDerecho(removeRec(nodoPadre.getDerecho(),nodoPadre.getElemento())); // redujo subarbol derecho.
                if (isUnbalanced(nodoPadre)) {
                    nodoPadre = rotarSimpleDerecha(nodoPadre);
                }
            }
            nodoPadre.setAltura(calcularAltura(nodoPadre));
            return nodoPadre;
        }
    }


    // ------------------ MIN y MAX VALUES --------------------
    public T findMax() {
        return findMax(raiz);
    }
    public T findMax(Nodo<T> nodoPadre) {
        // explora la derecha hasta que ya no haya más nodos a la derecha.
        if (nodoPadre.getDerecho() == null){
            return nodoPadre.getElemento();
        }else{
            return findMax(nodoPadre.getDerecho());
        }
    }

    public T findMin() {
        return findMin(raiz);
    }
    public T findMin(Nodo<T> nodoPadre) {
        // explora la izquierda hasta que ya no haya más nodos a la izquierda.
        if (nodoPadre.getIzquierdo() == null){
            return nodoPadre.getElemento();
        }else{
            return findMin(nodoPadre.getIzquierdo());
        }
    }

    // -------------------ROTACIONES--------------------
    public boolean isUnbalanced(Nodo<T> n) {
        return (abs( altura(n.getIzquierdo()) - altura(n.getDerecho()) ) == 2);
    }

    public Nodo<T> rotarSimpleDerecha(Nodo<T> raiz) {
        Nodo<T> temp = raiz.getIzquierdo();  // será nuestra nueva raíz después de alterar sus subárboles
        raiz.setIzquierdo(temp.getDerecho());
        temp.setDerecho(raiz);
        raiz.setAltura(max(altura(raiz.getIzquierdo()), altura(raiz.getDerecho())) + 1);
        temp.setAltura(max(altura(temp.getIzquierdo()), raiz.getAltura()) + 1);
        return temp;
    }

    public Nodo<T> rotarSimpleIzquierda(Nodo<T> raiz) {
        Nodo<T> temp = raiz.getDerecho();
        raiz.setDerecho(temp.getIzquierdo());
        temp.setIzquierdo(raiz);
        raiz.setAltura(max(altura(raiz.getIzquierdo()), altura(raiz.getDerecho())) + 1);
        temp.setAltura(max(altura(temp.getDerecho()), raiz.getAltura() ) + 1);
        return temp;
    }

    public Nodo<T> rotarDobleDerecha(Nodo<T> raiz) {
        raiz.setIzquierdo(rotarSimpleIzquierda(raiz.getIzquierdo()));
        return rotarSimpleDerecha(raiz);
    };

    public Nodo<T> rotarDobleIzquierda(Nodo<T> raiz) {
        raiz.setDerecho(rotarSimpleDerecha(raiz.getDerecho()));
        return rotarSimpleIzquierda(raiz);
    };

    public int altura(Nodo<T> nodo) {
        if (nodo == null) {
            return -1;
        } else {
            return nodo.getAltura();
        }
    }

    // ------------------OTROS MÉTODOS-------------------
    public int calcularAltura() {
        return calcularAltura(raiz);
    }
    public int calcularAltura(Nodo<T> n) {
        return calcularAlturaRec(n, 0);
    }
    private int calcularAlturaRec(Nodo<T> n, int count){
        if (n == null) {
           return -1;
        } else{
            count +=1;
            int alturaIzq = calcularAlturaRec(n.getIzquierdo(),0);
            int alturaDer = calcularAlturaRec(n.getDerecho(), 0);
            return count + max(alturaIzq, alturaDer);
        }
    }

    public int calcularProfundidad(Nodo<T> n) {
        return calcularProfundidadRec(n,raiz, 0);
    }
    private int calcularProfundidadRec(Nodo<T> n, Nodo<T> padre, int count) {
        if (n == null) {
            return -1;
        }
        if (n.getElemento() == padre.getElemento()) {
            return 0;
        } else {
            count++;
            if (n.getElemento().compareTo(padre.getElemento()) < 0) {
                return count + calcularProfundidadRec(n,padre.getIzquierdo(),0);
            }
            if (n.getElemento().compareTo(padre.getElemento()) > 0) {
                return count + calcularProfundidadRec(n,padre.getDerecho(),0);
            }
            return 0;
        }
    }

    public Nodo<T> findNode(T elemento, Nodo<T> nodoPadre) {
        // búsqueda binaria simple
        if (nodoPadre == null) {
            return null;
        }
        if (elemento.compareTo(nodoPadre.getElemento()) == 0) {
            return nodoPadre;
        } else {
            if (elemento.compareTo(nodoPadre.getElemento()) < 0) {
                return findNode(elemento, nodoPadre.getIzquierdo());
            }
            if (elemento.compareTo(nodoPadre.getElemento()) > 0) {
                return findNode(elemento, nodoPadre.getDerecho());
            }
            return null;
        }
    }
    public Nodo<T> findNode(T elemento) {
        return findNode(elemento, raiz);
    }

}