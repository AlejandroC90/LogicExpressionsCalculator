/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.calculadoradeexpresioneslogicas;
import java.util.Iterator;
import util.*;

/**
 *
 * @author alejandro - juan pablo
 */
public class ArbolExpresionLogica {
    
    private ArbolB arbol_logico; //arbol 
    private boolean valor_P,valor_Q,valor_R; //los valores lógicos de cada una de las variables (verdadero-falso)

    
    //constructor por default
    public ArbolExpresionLogica() {
    }

    public ArbolExpresionLogica(ListaCD<String> expresion,boolean valorp,
            boolean valorq,boolean valorr){
        Pila<String> stack=new Pila();
        Pila<String> salida=new Pila();
        Iterator expresion2=expresion.iterator();
        
        //AQUI LLENA LA PILA "SALIDA" CON LA EXPRESION EN POSFIJO
        cambiarExpresion(expresion2,stack, salida);
        
        this.valor_P=valorp;
        this.valor_Q=valorq;
        this.valor_R=valorr;
        
        //AQUI VA EL CODIGO PARA CREAR EL ARBOL CON LA PILA "SALIDA"
        crearArbolLogico(salida);
    }
    
    
    /**
     * Convierte la expresion Infija a una Posfija.
     * @param expresion Lista con cada uno de los caracteres de la expresion en 
     * infijo.
     * @param stack una Pila<String> con los operadores guardados, para ordenar 
     * la expresion.
     * @param salida La Pila<String> con los caracteres de la expresion 
     * ordenados en postfijo.
     */
     private void cambiarExpresion(Iterator expresion, Pila<String> stack,
             Pila<String> salida) {
         
         if(expresion.hasNext()){
             String dato=expresion.next().toString();
             
         if(dato.equalsIgnoreCase("p")||dato.equalsIgnoreCase("q")||
                 dato.equalsIgnoreCase("r")){
             salida.push(dato);
             cambiarExpresion(expresion,stack,salida);
             
         } else if(dato.equalsIgnoreCase("(")||dato.equalsIgnoreCase("~")){
             stack.push(dato);
             cambiarExpresion(expresion,stack,salida);
             
         } else if(dato.equalsIgnoreCase(")")){
             String a=stack.pop();
             
          while(!a.equalsIgnoreCase("(")){
              salida.push(a);
              a=stack.pop();
          }
             cambiarExpresion(expresion,stack,salida);
         } else{
             if(!stack.esVacio()){
                 
                 String a=stack.pop();
                 if(a.equalsIgnoreCase("(")){
                     stack.push(a);
                 } else if(a.equalsIgnoreCase("~")){
                     salida.push(a);
                     while(!stack.esVacio()){
                         a=stack.pop();
                         if(!a.equalsIgnoreCase("~")){
                             stack.push(a); break;
                         }
                         salida.push(a);
                     }
                 } else{
                     salida.push(a);
                 }
             }
             stack.push(dato);
             cambiarExpresion(expresion, stack, salida);
         }
         }
         while(!stack.esVacio()){
             salida.push(stack.pop());
         }
    }
    
    /**
     * Método que crea el árbol lógico con ayuda de una pila para guardar los nodos antes de pasar la raiz
     * al árbol binario.
     * 
     * @param pilaPostfija Pila que contiene la expresión en notación postfija
     */
    private void crearArbolLogico(Pila<String> pilaPostfija){
    	
    	Pila<NodoB> nodos = new Pila();
    	Pila<String> pilaPostfija2 = new Pila();
        
        while(!pilaPostfija.esVacio()){
            pilaPostfija2.push(pilaPostfija.pop());
        }
        
    	while(!pilaPostfija2.esVacio()){
    		
    		String dato = pilaPostfija2.pop();
    		
    		if(dato.equalsIgnoreCase("p")||dato.equalsIgnoreCase("q")||dato.equalsIgnoreCase("r")){
    			NodoB<String> nodo = new NodoB(dato,null, null);
    			nodos.push(nodo);
    		}
    		
    		if(dato.equalsIgnoreCase("v")||dato.equalsIgnoreCase("^")||dato.equalsIgnoreCase("->")||dato.equalsIgnoreCase("<->")){
    			
    			if(!nodos.esVacio()){
    			NodoB<String> nodo =new NodoB(dato,nodos.pop(),nodos.pop());
    			nodos.push(nodo);
    			}
    		}
    		
    		if(dato.equalsIgnoreCase("~")){
    			NodoB<String> nodo = new NodoB(dato,null,nodos.pop());
    			nodos.push(nodo);
    			
    		}
    		
    		  		
    		}
        
      
    	arbol_logico = new ArbolB(nodos.pop());
    		
    	}
    
    
    
       /**
        * Método decorador que envía la raíz del ábol a getPostOrden 
        * 
        * @return el método getPostOrden
        */ 
       public String getPostOrden(){
           
           NodoB raiz = this.arbol_logico.getRaiz();
           
           return getPostOrden(raiz);
       }
       
        /**
        * Método que devuelve un String con el postOrden del arbol de la expresión escrita en la GUI
        * 
        * @param raiz NodoB que es enviado por el método decorador getPosOrden, es la raíz del árbol 
        * @return un String con el postorden del árbol
        */
         private String getPostOrden(NodoB raiz){
           
           String msg = "";
        
        if(raiz==null){
            return "El árbol se encuentra vacío";
        }
        
        if(raiz.getIzq()!=null&&raiz.getDer()!=null){
            return msg += getPostOrden(raiz.getIzq()) + getPostOrden(raiz.getDer()) + raiz.getInfo();
        }
        
        if(raiz.getIzq()!=null){
            return msg +=  getPostOrden(raiz.getIzq())+ raiz.getInfo().toString();
        }
            
        if(raiz.getDer()!=null){
            return msg += raiz.getInfo().toString() + getPostOrden(raiz.getDer());
        }
        
        if(raiz.esHoja()){
            
            return msg += raiz.getInfo().toString();
        }
  
        
        return msg;
    }
    
    
       /**
        * Método decorador que enví la raíz del árbol al método getPreOrden
        * 
        * @return el método getPreOrden
        */
       public String getPreOrden(){
           
           NodoB raiz  = this.arbol_logico.getRaiz();
           
           
           return getPreOrden(raiz);
       }
       
        /**
        * Método que devuelve un String con el preOrden del arbol de la expresión escrita en la GUI
        * @param raiz NodoB que es enviado por el método decorador getPreOrden, es la raíz del árbol
        * @return 
        */
        private String getPreOrden(NodoB raiz){
           
          String msg = "";
        
        if(raiz==null){
            return "El árbol se encuentra vacío";
        }
        
        if(raiz.getIzq()!=null&&raiz.getDer()!=null){
            return msg += raiz.getInfo() +getPreOrden(raiz.getIzq()) + getPreOrden(raiz.getDer());
        }
        
        if(raiz.getIzq()!=null){
            return msg += raiz.getInfo().toString() + getPreOrden(raiz.getIzq());
        }
            
        if(raiz.getDer()!=null){
            return msg += raiz.getInfo().toString() + getPreOrden(raiz.getDer());
        }
        
        if(raiz.esHoja()){
            
            return msg += raiz.getInfo().toString();
        }
  
          return msg; 
           
         
       }
    
        /**
      * Método decorador de evaluarExpresion, el cual toma la raíz del árbol
      * @return el metodo evaluarExpresion
      */
       public boolean getEvaluar(){
           
           NodoB raiz = this.arbol_logico.getRaiz();
                   
           return evaluarExpresion(raiz);
       }
       
       /**
        * Método que permite saber el valor de la expresión lógica escrita por el usuario, dependiendo del valor
        * de cada una de las variable y de uso de los operadores lógicos.
        * @param raiz es la raiz de arbol enviada desde el método decorador getEvaluar
        * @return una variable de tipo boolean la cual es el resultado de evaluar la expresión
        */
       private boolean evaluarExpresion(NodoB raiz){
           
           
           //si es null, devuelve un false
           if(raiz==null)
               return false;
           //si tiene dos null es hoja, retornamos su valor
           if(raiz.esHoja())
               return convertir(raiz);
           //si tiene solo un hijo derecho, quiere decir que es una negacion, entonces retornamos lo inverso
           if(raiz.getIzq()!=null&&raiz.getDer()==null)
               return invertir(evaluarExpresion(raiz.getIzq())); 
           
           //
           if(raiz.getIzq()!=null&&raiz.getDer()!=null){
               
               return calcular(evaluarExpresion(raiz.getIzq()),evaluarExpresion(raiz.getDer()),raiz.getInfo().toString());
               
           }
           
           
           
           return false;
       }
       
        /**
        * Método que permite saber el valor lógico del resultado entre dos operadores y un operando
        * @param izq es el valor del operando izquierdo
        * @param der es el valor del operando derecho
        * @param expresion un String con la información del operando 
        * @return el valor del cálculo de la expresión
        */    
       private boolean calcular (boolean izq, boolean der, String expresion){
           
           if(expresion.equalsIgnoreCase("v")){
               
                 if(!izq&&!der){
                   return false;
               }
                 else return true;
           }
             if(expresion.equalsIgnoreCase("^")){
               
               if(izq&&der){
                    return true;
               }
               else return false;
               
             }
             if(expresion.equalsIgnoreCase("->")){
               
             
               if(izq&&!der){
                   return false;
               }
               else return true;
               
             }
             
             if(expresion.equalsIgnoreCase("<->")){
               
               if(izq&&der||!izq&&!der){
                   return true;
               }
              
               else return false;
             }
           
           return false;
       }
       
       /**
        * Éste método permite invertir una variable, se usa en el caso de "not"
        * @param i variable de tipo boolean a ser invertida
        * @return la varibale boolean pero invertida
        */
       private boolean invertir(boolean i){
           
           return (!i);          
           
       }
       
       /**
        * Método que permite cambiar el valor String de la info del NodoB por el valor lógico que corresponde  a su letra
        * @param nodo recibe el nodo hoja, para luego enviar su valor lógico
        * @return return una variable de tipo boolean con el valor lógico del nodo
        */
       private boolean convertir(NodoB nodo){
           
           String dato = nodo.getInfo().toString();
           
           if(dato.equalsIgnoreCase("p")){
               return this.valor_P;
           }
           if(dato.equalsIgnoreCase("q")){
               return this.valor_Q;
           }
           if(dato.equalsIgnoreCase("r")){
               return this.valor_R;
           }
           return false;
       }
    }

 
    
    
    
    

