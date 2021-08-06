package baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

public class BaseDatos {
	
//	Crea la variable global conexion.
	public static Connection conexion;

	public static void main(String[] args) {

		Scanner teclado = new Scanner(System.in);
		
		String baseDeDatos = "";
		String queryCreate = "";
		String queryInsert = "";
		String nombreTabla = "";
		int numeroAtributos = 0;
		int numeroTablas = 0;
		String queryCreateParteFinal = "";
		String queryPrimaryKey = "";
		String queryForeignKey = "";
		String respuesta = "";
		String dato = "";
		
//		Pide el nombre de la base de datos.
		System.out.println("Escriba nombre de la base de datos a crear:");
		baseDeDatos = teclado.next();
		
//		Crea la base de datos llamando a la funcion.
		crearBD(baseDeDatos);
		
//		Pide el numero de tablas a crear.
		System.out.println("\nEscriba el numero de tablas quiere crear para " +baseDeDatos+ ":");
		numeroTablas = teclado.nextInt();
		
//		Crea el array para el nombre de las tablas.
		String arrayNombreTablas[] = new String[numeroTablas];
		
//		Bucle que va piediendo el nombre de las tablas y las va creando.
		for (int i = 0; i < numeroTablas; i++) {
			
//			Pide el nonbre de la tabla. Guarda el nombre en un array.
			System.out.println("\nEscriba el nombre de la tabla: " +(i+1)+ ":");
			nombreTabla = teclado.next();
			
//			nombreTabla = arrayNombreTablas[i];
			arrayNombreTablas[i] = nombreTabla;
//			teclado.nextLine();
			
			System.out.println("\nEscriba el numero de atributos para la tabla: " + arrayNombreTablas[i]);
			numeroAtributos = teclado.nextInt();
			
			System.out.println("\nIndique el numero de claves primarias (Primary keys) de la tabla: " + arrayNombreTablas[i]);
			int numeroPrimaryKeys = teclado.nextInt();
			
//			Array de primary keys.
			String primaryKeys[] = new String[numeroPrimaryKeys];
			
//			Bucle para los atributos.
			for (int j = 0; j < numeroAtributos; j++) {
				
				int menu = 0;
				int tamanyo = 0;
				String respuestaPrimaryKey = "";
				boolean esPrimaryKey = false;
				
				do {
				System.out.println("\nIndique tipo de dato a introducir para el atributo " +(j+1)+ " :"
									+ "\n1- varchar"
									+ "\n2- nvarchar"
									+ "\n3- int"
									+ "\n4- char"
									+ "\n5- datetime");
				menu = teclado.nextInt();
				}while(menu <= 0 || menu >= 6);
				
//				Prepara la sentencia dato a dato segun opcion seleccionada.
				switch (menu) {
				
//				CASO VARCHAR.
				case 1:
//					Pide el nombre y tamaño del varchar.
					System.out.println("\nEscriba el nombre del varchar:");
					dato = teclado.next();
					
					System.out.println("\nEscriba el tamaño del varchar:");
					tamanyo = teclado.nextInt();
					
					/**PRIMARY KEY
					 */
//					Pregunta si el dato introducido es primary key.
					do {
						System.out.println("\n¿Es " +dato+ " primary key? \n S/N");
						respuestaPrimaryKey = teclado.next();
						
					}while(!respuestaPrimaryKey.equalsIgnoreCase("S") && !respuestaPrimaryKey.equalsIgnoreCase("N"));
					
//					Si el dato es primary key.
					if(respuestaPrimaryKey.equalsIgnoreCase("S")) {
						
//						Confirma que es primary key.
						esPrimaryKey = true;
						
//						Guarda en una variable parte de la query.
//						queryPrimaryKey = " PRIMARY KEY("+dato+")";
						primaryKeys[j] = dato;
						
//						Guarda en una variable parte de la query.
						queryCreateParteFinal += dato+" varchar("+tamanyo+"),";
					
//					Mensaje en caso de no haber primary key.
					}else if(respuestaPrimaryKey.equalsIgnoreCase("N")) {
//						Guarda en una variable parte de la query.
						queryCreateParteFinal += dato+" varchar("+tamanyo+"),";
//						Mensaje.
						System.out.println("\nPrimary key no añadida.");
						
					}
					
					break;
				
					
//				CASO NVARCHAR.
				case 2:
					System.out.println("\nEscriba el nombre del nvarchar:");
					dato = teclado.next();
					
					System.out.println("\nEscriba el tamaño del nvarchar:");
					tamanyo = teclado.nextInt();
					
					/**PRIMARY KEY
					 */
					do {
						System.out.println("\n¿Es " +dato+ " primary key? \n S/N");
						respuestaPrimaryKey = teclado.next();
						
					}while(!respuestaPrimaryKey.equalsIgnoreCase("S") && !respuestaPrimaryKey.equalsIgnoreCase("N"));
					
					if(respuestaPrimaryKey.equalsIgnoreCase("S")) {
						
						esPrimaryKey = true;
						
						queryPrimaryKey = dato;
						
						queryCreateParteFinal += dato+" nvarchar("+tamanyo+"),";
					
					}else if(respuestaPrimaryKey.equalsIgnoreCase("N")) {
						queryCreateParteFinal += dato+" nvarchar("+tamanyo+"),";
						System.out.println("\nPrimary key no añadida.");
						
					}

					break;
					
					
//				CASO INTEGER.
				case 3:
					System.out.println("\nEscriba el nombre del int:");
					dato = teclado.next();
					
					/**PRIMARY KEY
					 */
					do {
						System.out.println("\n¿Es " +dato+ " primary key? \n S/N");
						respuestaPrimaryKey = teclado.next();
						
					}while(!respuestaPrimaryKey.equalsIgnoreCase("S") && !respuestaPrimaryKey.equalsIgnoreCase("N"));
					
					if(respuestaPrimaryKey.equalsIgnoreCase("S")) {
						
						esPrimaryKey = true;
						
//						queryPrimaryKey = " PRIMARY KEY("+dato+")";
						primaryKeys[j] = dato;
						
						queryCreateParteFinal += dato+" int,";
					
					}else if(respuestaPrimaryKey.equalsIgnoreCase("N")) {
						queryCreateParteFinal += dato+" int,";
						System.out.println("\nPrimary key no añadida.");
						
					}

					break;
					
					
//				CASO CHAR.
				case 4:
//				Pide el nombre y tamaño del varchar.
				System.out.println("\nEscriba el nombre del char:");
				dato = teclado.next();
				
				System.out.println("\nEscriba el tamaño del char:");
				tamanyo = teclado.nextInt();
				
				/**PRIMARY KEY
				 */
//				Pregunta si el dato introducido es primary key.
				do {
					System.out.println("\n¿Es " +dato+ " primary key? \n S/N");
					respuestaPrimaryKey = teclado.next();
					
				}while(!respuestaPrimaryKey.equalsIgnoreCase("S") && !respuestaPrimaryKey.equalsIgnoreCase("N"));
				
//				Si el dato es primary key.
				if(respuestaPrimaryKey.equalsIgnoreCase("S")) {
					
//					Confirma que es primary key.
					esPrimaryKey = true;
					
//					Guarda en una variable parte de la query.
//					queryPrimaryKey = " PRIMARY KEY("+dato+")";
					primaryKeys[j] = dato;
					
//					Guarda en una variable parte de la query.
					queryCreateParteFinal += dato+" char("+tamanyo+"),";
				
//					Mensaje en caso de no haber primary key.
				}else if(respuestaPrimaryKey.equalsIgnoreCase("N")) {
//						Guarda en una variable parte de la query.
					queryCreateParteFinal += dato+" char("+tamanyo+"),";
//						Mensaje.
					System.out.println("\nPrimary key no añadida.");
					
				}

				break;
				
				
//				CASO DATETIME.
				case 5:
				System.out.println("\nEscriba el nombre del datetime:");
				dato = teclado.next();
				
				/**PRIMARY KEY
				 */
				do {
					System.out.println("\n¿Es " +dato+ " primary key? \n S/N");
					respuestaPrimaryKey = teclado.next();
					
				}while(!respuestaPrimaryKey.equalsIgnoreCase("S") && !respuestaPrimaryKey.equalsIgnoreCase("N"));
				
				if(respuestaPrimaryKey.equalsIgnoreCase("S")) {
					
					esPrimaryKey = true;
					
					primaryKeys[j] = dato;
					
					queryCreateParteFinal += dato+" datetime,";
				
				}else if(respuestaPrimaryKey.equalsIgnoreCase("N")) {
					queryCreateParteFinal += dato+" datetime,";
					System.out.println("\nPrimary key no añadida.");
					
				}
				
				
				break;

					
				default:
					System.out.println("\nOpcion seleccionada incorrecta.");
					break;
				}
				
				
				/**CLAVE FORANEA, FOREIGN KEY.
				 */
				// Pregunta si hay claves foraneas una vez terminado el nuemro de atributos de la tabla.
				if((j+1) == numeroAtributos) {
					/**
					 * RECORRE EL ARRAY DE FOREIGN KEYS PARA CREAR LA QUERY FINAL DE FOREIGN KEYS.
					 */
//					Recoge el array que llega de la funcion foreignkey lleno de sentencias de clave foranea.
					String arrayForeignKeys[] = foreignKey(nombreTabla);
					int cnt=0;
					
//					Recorre el array de foreign keys y lo almacena en la variable que forma la query final.
					for (int k = 0; k < arrayForeignKeys.length; k++) {
						queryForeignKey += arrayForeignKeys[k];
						cnt++;
						if(cnt > 0 && cnt < arrayForeignKeys.length) {
							queryForeignKey += ",";
						}
					}
				}
			}
			
			
			/** COMO OBTENER LA QUERY DE PRIMARY KEYS.
			 *	Se crean 2 String que se usan para cabecera de sentencia y la coma entre variables en caso de ser mas de una primary Key.
			 *	Mientras se recorre el array de primary keys en base a su longitud para ir añadiendolas a un String de cabecera, 
			 *	para luego juntarlos y crear la query final.
			 */
			String comienzoQueryPrimary = " PRIMARY KEY(";
			String finalQueryPrimary = ",";
			int contador=0;
			
			for (int k = 0; k < primaryKeys.length; k++) {
				contador++;
				
				if(primaryKeys[k] != null) {
					comienzoQueryPrimary += primaryKeys[k];
					
					if( contador > 0 && contador < primaryKeys.length) {
						comienzoQueryPrimary += finalQueryPrimary;
						
					}
				}
				queryPrimaryKey = comienzoQueryPrimary;
			}
			
			
//			Prepara la query final.
			if(primaryKeys.length >= 1){
				if(queryForeignKey.equalsIgnoreCase("no")) {
					queryCreate = "CREATE TABLE IF NOT EXISTS " + nombreTabla +" ("+ queryCreateParteFinal + queryPrimaryKey + ") );";
					
				}else if(!queryForeignKey.equalsIgnoreCase("no")){
					queryCreate = "CREATE TABLE IF NOT EXISTS " + nombreTabla +" ("+ queryCreateParteFinal + queryPrimaryKey + ")," + queryForeignKey + ");";
				}
				
				
			}
			
//			Muestra la query.
			System.out.println("\nQUERY: " + queryCreate);

//			Crea la tabla llamando a la funcion pasandole el nombre de la base de datos y la query.
			crearTablas(baseDeDatos, queryCreate);
			
//			IMPORTANTE --> Resetea las query para la siguiente tabla!!!
			queryCreate = "";
			queryCreateParteFinal = "";
			queryPrimaryKey = "";
			queryForeignKey = "";
		}
		
		
		/**
		 * INSERTAR DATOS EN LAS TABLAS. 
		 * IMPORTANTE---> SE TIENE QUE ESCRIBIR LA QUERY ENTERA DEL INSERT.
		 */
//		Resetea la respuesta anterior para evitar conflictos.
		respuesta = "";
		
//		Pregunta si quiere insertar datos en las tablas.
		do {
			System.out.println("¿Insertar datos en las tablas? \nS/N ");
			respuesta = teclado.next();
			
		}while(!respuesta.equalsIgnoreCase("S") && !respuesta.equalsIgnoreCase("N"));
		
//		Guarda la query con una coma al final dependiendo de si es o no el ultimo dato de la tabla.
		if(respuesta.equalsIgnoreCase("S")) {
			
			System.out.println("Escriba el numero TOTAL de inserciones que se van a realizar:");
			int numeroInserciones = teclado.nextInt();
			
			for (int i = 0; i < numeroInserciones; i++) {
				
				System.out.println("IMPORTANTE --> SE TIENE QUE ESCRIBIR LA QUERY ENTERA DEL INSERT.");
				queryInsert += teclado.next();
				System.out.println(queryInsert);
//				Llama a la funcion que inserta datos en la tabla.
				insertarDatos(baseDeDatos, queryInsert);
			}
			
			System.out.println("Inserciones realizadas.");
			
		}else if(respuesta.equalsIgnoreCase("N")) {
//			Mensaje
			System.out.println("No se insertaran datos en las tablas de " +baseDeDatos+ ".");
			
		}
		
	}
	

//	Funcion que recibe el nombre de la tabla y retorna la query de la foreign key.
	public static String[] foreignKey(String nombreTabla) {
		
		Scanner teclado = new Scanner(System.in);
		
		String respuestaForeignKey = "";
		String queryForeignKey = "";
		String foreignKeys[] = new String[0];
		
//		Pregunta si hay foreign keys en la tabla actual.
		do {
			System.out.println("\n¿Hay foreign keys en la tabla " +nombreTabla+ "? \n S/N");
			respuestaForeignKey = teclado.next();
			
		}while(!respuestaForeignKey.equalsIgnoreCase("S") && !respuestaForeignKey.equalsIgnoreCase("N"));
		
//		Si responde que si..contadorForeignKeys.
		if(respuestaForeignKey.equalsIgnoreCase("S")) {
			
//			Mensaje de advertencia.
			System.out.println("\nIMPORTANTE: Seguir las normas o de lo contrario "
							+ "\nla base de datos NO sera creada.");
			
//			Pregunta cuantas claves hay para asignar el tamaño al array de foreign keys.
			System.out.println("\nEscriba el numero de foreign keys que habra en la tabla " +nombreTabla+ ".");
			int numeroForeignKeys = teclado.nextInt();
			
//			Asigna el tamaño del array de foreign keys.
			foreignKeys = new String[numeroForeignKeys];
			
//			Comienza un bucle para ir guardando las claves en el array e ir montando la query.
			for (int i = 0; i < numeroForeignKeys; i++) {
				
//				Pide el nombre de la clave foranea.
				System.out.println("\nEscriba el nombre de la clave foranea " + (i+1) + " de la tabla " +nombreTabla+ ":");
				String nombreForeignKey = teclado.next();
				
//				Pregunta que tipo de dato es.
				System.out.println("\nEscriba que tipo de dato es"
						+ "\nvarchar"
						+ "\nvarchar"
						+ "\nint"
						+ "\nchar"
						+ "\ndatetime");
				String tipoDato = teclado.next();
				
//				Pide el nombre de la tabla al que apunta la foreign key.
				System.out.println("\nEscriba el nombre de la tabla a la que apunta la foreign key " +nombreForeignKey+ ":");
				String nombreTablaApuntado = teclado.next();
				
//				Pide el nombre de la primary key al que apunta la foreign key.
				System.out.println("\nEscriba el nombre de la primary key a la que apunta " +nombreForeignKey+ ".");
				String nombreApuntado = teclado.next();
				
//				Construye la query de la foreign key.
				queryForeignKey = " FOREIGN KEY(" +nombreForeignKey+ ") REFERENCES " +nombreTablaApuntado+ "(" +nombreApuntado+")";
				
				foreignKeys[i] = queryForeignKey;

			}
		
		}else {
//			Asigna el tamaño del array de foreign keys.
			foreignKeys = new String[1];
			foreignKeys[0] = "no";
			System.out.println(queryForeignKey + "\nForeign key no añadida.");
			
		}
		
		// Retorna la query en formato String.
		return foreignKeys;
		
			
	}
	
	
//	Conecta con MySQL.
	public static void conectar() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://192.168.0.30:3306?userTimezone=true&serverTimezone=UTC","remote","aZsX1!dC");
			
			System.out.println("Connected!");
			
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Error 404");
			System.out.println(e);
			
		}
	}
	
//	Desconecta de MySQL.
	public static void cierraConexion() {
		
		try {
			conexion.close();
			
			System.out.println("Disconnected!");
			
		} catch (SQLException e) {
			System.out.println(e);
			
		}
	}
	
//	Funcion para crera la base de datos.
	public static void crearBD(String nombre) {
		
		conectar();
		
		try {
			String Query = "Create Database " + nombre;
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			
			System.out.println("La base de datos " + nombre + " ha sido creada.");
			
			cierraConexion();
			
		} catch(SQLException e) {
			System.out.println("Error");
			System.out.println(e);
			
		}
	}
	
//	Funcion para crear las tablas.
	public static void crearTablas(String db, String Query) {
		
		conectar();
		
		try {
			String QueryDB = "USE " + db;
			Statement stdb = conexion.createStatement();
			stdb.executeUpdate(QueryDB);
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			
			System.out.println("Tabla creada.");
			
			
		} catch (SQLException e) {
			System.out.println("Error");
			System.out.println(e);
			
		}
	}
	
//	Funcion para insertar datos en las tablas.
	public static void insertarDatos (String db, String Query) {
		
		conectar();
		
		try {
			String QueryDB = "USE " + db;
			Statement stdb = conexion.createStatement();
			stdb.executeUpdate(QueryDB);
			
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			
			System.out.println("Datos insertados correctamente.");
			
		} catch (SQLException e) {
			System.out.println("Error");
			System.out.println(e);
			
		}
	}
}