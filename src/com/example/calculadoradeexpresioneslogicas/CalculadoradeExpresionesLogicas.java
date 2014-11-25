package com.example.calculadoradeexpresioneslogicas;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;
import util.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


public class CalculadoradeExpresionesLogicas extends Activity {
	
	
	
	//private EditText pantalla;
	private TextView pantalla, salida,valorlogico;
	String expresion= "";
	private ArbolExpresionLogica arbol;
	private Button guardar;
    private Button botonp,botonq,botonr,abrir,cerrar,negar,implica,dobleimplica,or,and,borrar,borrartodo;
	private CheckBox valorp,valorq,valorr;
    float tamañoletrainicail;

	// private Button btn = (Button) findViewById(R.id.boton4);
	// private NodoB b = new NodoB("p",null,null);
	 // private String expresion = "";
		
	
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculadorade_expresiones_logicas);
		
		
		
		this.arbol = new ArbolExpresionLogica();

		this.pantalla = (TextView) findViewById(R.id.pantalla);

		this.guardar = (Button) findViewById(R.id.terminado);
        this.botonp = (Button) findViewById(R.id.botonp);
        this.botonq = (Button) findViewById(R.id.botonq);
        this.botonr = (Button) findViewById(R.id.botonr);
        this.abrir = (Button) findViewById(R.id.abrir);
        this.cerrar = (Button) findViewById(R.id.cerrar);
        this.negar = (Button) findViewById(R.id.nega);
        this.implica = (Button) findViewById(R.id.implica);
        this.dobleimplica = (Button) findViewById(R.id.doble);
        this.or = (Button) findViewById(R.id.or);
        this.and = (Button) findViewById(R.id.and);
        this.borrar = (Button) findViewById(R.id.borrar);
        this.borrartodo=   (Button) findViewById(R.id.borrartodo);

		
		this.valorp = (CheckBox) findViewById(R.id.valorp);
		this.valorq = (CheckBox) findViewById(R.id.valorq);
		this.valorr = (CheckBox) findViewById(R.id.valorr);
		//this.salida = (TextView) findViewById(R.id.salida);
        this.tamañoletrainicail = pantalla.getTextSize();

        this.addListenerOnvalorp();
        this.addListenerOnvalorq();
        this.addListenerOnvalorr();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_calculadorade_expresiones_logicas, menu);

		return true;
	}
	

	
	public void sumarp(View view){
		String expresion = (String) pantalla.getText();
	//	Editable mensaje = this.pantalla.getText();
		//String mensaje = "No creo que vayas a seguir trolleando";
		pantalla.setText(expresion +"p");
        this.cambiarTamañoletraPantalla();
			
	}
	
	public void sumarq(View view){
		String expresion = (String) pantalla.getText();
		 pantalla.setText(expresion +"q");
        this.cambiarTamañoletraPantalla();
			
	}
	
	public void sumarr(View view){
		String expresion = (String) pantalla.getText();
	    pantalla.setText(expresion +"r");
        this.cambiarTamañoletraPantalla();
			
	}
	
	public void sumarimpli(View view){
		String expresion = (String) pantalla.getText();
	    pantalla.setText(expresion +"->");
        this.cambiarTamañoletraPantalla();
			
	}
	
	public void sumarabrir(View view){
		String expresion = (String) pantalla.getText();
	    pantalla.setText(expresion +"(");
        this.cambiarTamañoletraPantalla();
			
	}
	
	public void sumarcerrar(View view){
		String expresion = (String) pantalla.getText();
	    pantalla.setText(expresion +")");
        this.cambiarTamañoletraPantalla();
			
	}
	
	public void sumardoble(View view){
		String expresion = (String) pantalla.getText();
	    pantalla.setText(expresion +"<->");
        this.cambiarTamañoletraPantalla();
			
	}
	
	public void sumarnega(View view){
		String expresion = (String) pantalla.getText();
	    pantalla.setText(expresion +"~");
        this.cambiarTamañoletraPantalla();
			
	}
	
	public void sumaror(View view){
		String expresion = (String) pantalla.getText();
	    pantalla.setText(expresion +"v");
        this.cambiarTamañoletraPantalla();
			
	} 
	public void sumarand(View view){
		String expresion = (String) pantalla.getText();
	    pantalla.setText(expresion +"^");
        this.cambiarTamañoletraPantalla();
			
	}
	public void borrartodo(View view){
		String expresion = "";
	    pantalla.setText(expresion);
        if(this.pantalla.getTextSize()==60){
            this.pantalla.setTextSize(100);
        }
			
	}
	public void borrar(View view){
		
		String texto = (String) pantalla.getText();


		
		   char[] cr=texto.toCharArray();
	        String msg="";
	        int size=cr.length;

        if(size<0){

            if(this.pantalla.getTextSize()==60){
            this.pantalla.setTextSize(100);
            }
        }


	        if(cr.length>0){
	        if(cr[size-1]=='>'){
	            if(size<3||cr[size-3]=='<'){
	                size-=2;
	            }else{
	                size--;
	            }
	        }
	        for(int i=0;i<size-1;i++){
	            msg+=cr[i]+"";
	        }
	        }
	        
		
		
		
		
	    pantalla.setText(msg);
			
	}
	
	 public void guardar(View view) {

         if(this.guardar.getText().toString().equalsIgnoreCase("Reiniciar")){
             this.reiniciar();
             this.activarBotones();
             return;

         }
	        boolean p,q,r;
	       if(valorp.isChecked()){
	            p = true;
	       }
	        else p=  false;
	        
	        if(valorq.isChecked()){
	            q = true;
	        }
	        else q=  false;
	        
	        if(valorr.isChecked()){
	            r = true;
	        }
	        else r=  false;
	        
	        String cadena = (String) pantalla.getText();
	        if(cadena.isEmpty()){
	        	String m ="No ha digitado ninguna expresión...";

                Toast.makeText(getApplicationContext(), m, 3000).show();
	          return;
	        }
	        
	        
	       
	        char c[]=cadena.toCharArray();
	        ListaCD<String> expresion=new ListaCD<String>();
	        
	        for(int i=(c.length-1);i>=0;i--){
	            if(c[i]=='>'){
	                
	                if((i-2)>-1&&c[i-2]=='<'){
	                    expresion.addInicio((c[i-2]+"")+(c[i-1]+"")+(c[i]+""));
	                    i-=2;
	                }else {
	                    expresion.addInicio((c[i-1]+"")+(c[i]+""));
	                    i--;
	                }
	                
	            }else{
	                expresion.addInicio(c[i]+"");
	            }
	        }
	        
	        if(validarExpresion(expresion)){
	            this.arbol=new ArbolExpresionLogica(expresion,p,q,r);
	            String m = "Expresión: " +pantalla.getText()+ "\n" + "Prefijo: " + arbol.getPreOrden() +"\n" + "Postfijo: " + arbol.getPostOrden() +"\n" + "Evaluación: " + arbol.getEvaluar();
	            this.pantalla.setTextSize(40);
                this.pantalla.setMaxLines(4);
                this.pantalla.setText(m);

                this.guardar.setText("Reiniciar");
                this.desactivarBotones();
               // Toast.makeText(getApplicationContext(), "Hola?", 3000).show();
            }else{
                String m ="Expresión mal escrita";

                Toast.makeText(getApplicationContext(), m, 3000).show();
	            return;
	        }
	        
	        //this.guardar.setEnabled(false);


	        
	        
	    }
	
	 
	 private boolean validarExpresion(ListaCD<String> expresion){
	        
	        if(expresion.getSize()<2){
	            return false;
	        }
	        
	        String [] expresionArray=new String[expresion.getSize()];
	        int k=0;
	        for(String exp:expresion){
	            expresionArray[k]=exp; k++;
	        }
	        Pila<String> ValidarParent=new Pila<String>();
	        
	        for(int i=0;i<expresionArray.length-1;i++){
	            String first=expresionArray[i]; 
	            String next=expresionArray[i+1];
	            
	            if((!next.equalsIgnoreCase("p"))&&(!next.equalsIgnoreCase("q"))&&
	                    (!next.equalsIgnoreCase("r"))&&(!next.equalsIgnoreCase(")"))){
	                if((i+2)==(expresionArray.length))
	                {return false;}
	            }
	            
	            if(first.equalsIgnoreCase("q")||first.equalsIgnoreCase("p")||
	                    first.equalsIgnoreCase("r")){
	                
	                if(next.equalsIgnoreCase("q")||next.equalsIgnoreCase("p")||
	                        next.equalsIgnoreCase("r")||next.equalsIgnoreCase("~")||
	                        next.equalsIgnoreCase("("))
	                {return false;}
	                
	            } else if(first.equalsIgnoreCase("v")||first.equalsIgnoreCase("^")||
	                    first.equalsIgnoreCase("->")||first.equalsIgnoreCase("<->")
	                    ||first.equalsIgnoreCase("~")){
	                
	                if(next.equalsIgnoreCase("v")||next.equalsIgnoreCase("^")||
	                        next.equalsIgnoreCase("->")||next.equalsIgnoreCase("<->")||
	                        next.equalsIgnoreCase(")"))
	                {return false;}
	                
	            } else if(first.equalsIgnoreCase("(")){
	                ValidarParent.push("(");
	                
	                if(next.equalsIgnoreCase("^")||next.equalsIgnoreCase("v")||
	                        next.equalsIgnoreCase("->")||next.equalsIgnoreCase("<->")){
	                    return false;}
	                
	            } else if(first.equalsIgnoreCase(")")){
	                ValidarParent.push(")");
	                
	                if(next.equalsIgnoreCase("p")||next.equalsIgnoreCase("q")||
	                        next.equalsIgnoreCase("r")||next.equalsIgnoreCase("~")||
	                        next.equalsIgnoreCase("("))
	                {return false;}
	                
	            }
	        }
	        
	        int PrAbierto=0, PrCerrado=0;
	        while(!ValidarParent.esVacio()){
	            String parent=ValidarParent.pop();
	            if(parent.equalsIgnoreCase("(")){
	                PrAbierto++;
	            }else{
	                PrCerrado++;
	            }
	        }
	        if(PrAbierto!=PrCerrado)
	        {return false;}
	        
	        return true;
	    }
	 
	 
	 public void prefijo (View view){
		 String msg = (this.arbol.getPreOrden());
		 
		 this.salida.setText(msg);
	 }
	 
	 public void postfijo (View view){
		 String msg = (this.arbol.getPostOrden());
		 
		 this.salida.setText(msg);
	 }
	 
	 public void evaluar (View view){
		 boolean valor = (this.arbol.getEvaluar());
		 
		 if(valor==true){
			valorlogico.setTextColor(Color.parseColor("#99CC00"));
			  
		 }
		 valorlogico.setTextColor(Color.parseColor("#CC0000"));
		 
		 this.valorlogico.setText(String.valueOf(valor));
	 }
	 
	 
	 public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle item selection
		    switch (item.getItemId()) {
		        case R.id.acerca:
		            Intent i = new Intent(this,AcercaDe.class);

		            startActivity(i);
		            return true;
		        	
		    }
			return false;
		}



    public void addListenerOnvalorp(){

        this.valorp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox)view).isChecked()){
                    String m = "El valor de p ahora es Verdadero";
                    Toast.makeText(getApplicationContext(), m, 3000).show();
                } else {
                String m = "El valor de p ahora es Falso";
                Toast.makeText(getApplicationContext(), m, 3000).show();
                }

            }
        });




    }
    public void addListenerOnvalorq(){

        this.valorq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox)view).isChecked()){
                    String m = "El valor de q ahora es Verdadero";
                    Toast.makeText(getApplicationContext(), m, 3000).show();
                } else {
                    String m = "El valor de q ahora es Falso";
                    Toast.makeText(getApplicationContext(), m, 3000).show();
                }

            }
        });




    }
    public void addListenerOnvalorr(){

        this.valorr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox)view).isChecked()){
                    String m = "El valor de r ahora es Verdadero";
                    Toast.makeText(getApplicationContext(), m, 3000).show();
                } else {
                    String m = "El valor de r ahora es Falso";
                    Toast.makeText(getApplicationContext(), m, 3000).show();
                }

            }
        });
    }

     public void cambiarTamañoletraPantalla(){

         if(this.tamañoletrainicail==100){
        this.pantalla.setTextSize(60);

         }
    }


    public void reiniciar (){
        this.pantalla.setText("");
        this.guardar.setText("Terminado");


    }

    public void desactivarBotones(){



        this.botonp.setEnabled(false);
        this.botonq.setEnabled(false);
        this.botonr.setEnabled(false);
        this.abrir.setEnabled(false);
        this.cerrar.setEnabled(false);
        this.negar.setEnabled(false);
        this.implica.setEnabled(false);
        this.dobleimplica.setEnabled(false);
        this.or.setEnabled(false);
        this.and.setEnabled(false);
        this.borrar.setEnabled(false);
        this.borrartodo.setEnabled(false);

    }

    public void activarBotones(){



        this.botonp.setEnabled(true);
        this.botonq.setEnabled(true);
        this.botonr.setEnabled(true);
        this.abrir.setEnabled(true);
        this.cerrar.setEnabled(true);
        this.negar.setEnabled(true);
        this.implica.setEnabled(true);
        this.dobleimplica.setEnabled(true);
        this.or.setEnabled(true);
        this.and.setEnabled(true);
        this.borrar.setEnabled(true);
        this.borrartodo.setEnabled(true);
    }
		
}