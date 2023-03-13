import java.util.List;
import java.util.ArrayList;

/**
 * Modela el Carcaj que lleva el personaje para
 * tranportar sus flechas.
 */
public class Carcaj extends Recipiente implements Portable {
    private final Integer PESO_PROPIO = 10;
    private Integer capacidad;
    private List<Flecha> flechas;
    private EstadoContenedor estado;

    /**
     * Constructor para un Carcaj generico chico. Su capacidad
     * es de 5 flechas y se inicializa en estado vacio (ver
     * Arroyo y Barrica a modo de ejemplo de los estados).
     * Recordar que tiene peso inicial.
     */
    public Carcaj () {
        // TODO - Implementar constructor
        setNombre("Carcaj chico");
        setPeso(PESO_PROPIO);
        capacidad = 5;
        estado = new Vacio();
        flechas = new ArrayList<>();
    }
    
    /**
     * Constructor para un Carcaj de nombre y capacidad indicados
     * por parametro.  Se inicializa en estado vacio (ver
     * Arroyo y Barrica a modo de ejemplo de los estados)
     * Recordar que tiene peso inicial.
     *
     * @param nombre El nombre del carcaj.
     * @param capacidad Cantidad maxima de flechas.
     */
    public Carcaj (String nombre, Integer capacidad) {
        // TODO - Implementar constructor
        setNombre(nombre);
        this.capacidad = capacidad;
        setPeso(PESO_PROPIO);
        estado = new Vacio();
        flechas = new ArrayList<>();
    }
    
/*
 ***** Patron State *****
 */
    /**
     * Delegate methods para acceder a los metodos apropiados
     * en base al estado del objeto.
     * 
     * Agrega una flecha al carcaj.
     * 
     * Debe actualizarse el peso total.
     * 
     * @param flecha La flecha a agregar.
     * @throws ContenedorLlenoException En caso que el carcaj se
     *               encuentre en su capacidad maxima con el
     *               mensaje: "Carcaj lleno".
     * @throws AccionNoPermitidaException En caso que el elemento no sea
     *               una flecha  con el mensaje: "Carcaj no admite <elemento>"
     *               (<elemento> == nombre del elemento).
     */
    public void addFlecha(Elemento elemento) throws ContenedorLlenoException, AccionNoPermitidaException {
            // delegate method a getElemento() de la interfaz
            estado.addElemento(elemento);
    
    }
    @Override
        public void addElemento(Elemento elemento) throws ContenedorLlenoException, AccionNoPermitidaException {
            // TODO - Implementar metodo
            estado.addElemento(elemento);
        }

    /**
     * Delegate methods para acceder a los metodos apropiados
     * en base al estado del objeto.
     * 
     * Devuelve una flecha del carcaj como Elemento.
     * 
     * Debe actualizarse el peso total.
     * 
     * @return Una flecha.
     * @throws ContenedorVacioException En caso que el carcaj se
     *               encuentre vacio con el mensaje: "Carcaj vacio".
     * @throws AccionNoPermitidaException No aplica.
     */
    public Elemento getFlecha () throws ContenedorVacioException, AccionNoPermitidaException {
        // delegate method a getElemento() segun estado
        return estado.getElemento();
    }
    @Override
    public Elemento getElemento () throws ContenedorVacioException, AccionNoPermitidaException {
        // delegate method a getElemento() segun estado
        return estado.getElemento();
    }

    /**
     * Se utiliza la version implementada en EstadoContenedor,
     * que siempre lanza la excepcion AccionNoPermitidaException.
     */    
    @Override
    public Elemento getElemento (String nombre) throws ContenedorVacioException, AccionNoPermitidaException {
        // delegate method a getElemento() segun estado
        return estado.getElemento(nombre);
    }


    /**
     * Implementar los metodos de las clases privadas que sean
     * necesarios para cada estado.
     * 
     * No olvidar contemplar las situaciones que producen
     * el cambio a otro estado.
     * 
     * Ver lo implementado en Arroyo y Barrica. Considerar
     * las pautas en Botella.
     */

    /**
     * La clase VACIO indica el estado del Carcaj sin flechas.
     * Debe cambiar al estado CONFLECHAS al agregarse una flecha.
     */
    // TODO - Implementar la clase privada
    private class Vacio extends EstadoContenedor {
        @Override
        public void addElemento(Elemento elemento) throws ContenedorLlenoException, AccionNoPermitidaException {
            // TODO - Implementar metodo
            int pesoActualizado;
            pesoActualizado= 0;
            if(elemento instanceof Flecha){
               flechas.add((Flecha) elemento);
            for(Flecha flecha : flechas){
                pesoActualizado = pesoActualizado + flecha.getPeso();
            }
            setPeso(PESO_PROPIO + pesoActualizado); 
              estado = new ConFlechas();
          }else {
              throw new AccionNoPermitidaException("Carcaj no admite " + elemento.getNombre());
          }
        }

        @Override
        public Elemento getElemento() throws ContenedorVacioException, AccionNoPermitidaException {
            // TODO - Implementar metodo
            throw new ContenedorVacioException("Carcaj vacio");
        }
        
        @Override
        public String toString() {
            return " sin flechas (vacio)";
         }
    }

    /**
     * La clase CONFLECHAS indica el estado del Carcaj con flechas.
     * Debe cambiar al estado VACIO al quitarse la ultima flecha.
     * Debe cambiar al estado LLENO al agregar una flecha que complete
     * la capacidad maxima.
     */
    // TODO - Implementar la clase privada
    private class ConFlechas extends EstadoContenedor {
        @Override
        public void addElemento(Elemento elemento) throws ContenedorLlenoException, AccionNoPermitidaException {
            // TODO - Implementar metodo
            int pesoActualizado;
            pesoActualizado =0;
            if(getCantidadFlechas()==(capacidad-1)){
                if(elemento instanceof Flecha){
                    estado = new Lleno();
                    flechas.add((Flecha) elemento);
                }else {
                    throw new AccionNoPermitidaException("Carcaj no admite " + elemento);
                }
            }else if(elemento instanceof Flecha) {
                flechas.add((Flecha) elemento);
            }else {
                throw new AccionNoPermitidaException("Carcaj no admite " + elemento);
            }
            for(Flecha  flecha : flechas){
                pesoActualizado = pesoActualizado + flecha.getPeso();
            }
            setPeso(PESO_PROPIO + pesoActualizado);
        }
        
        @Override
        public Elemento getElemento() throws ContenedorVacioException, AccionNoPermitidaException {
            // TODO - Implementar metodo
            int pesoActualizado;
            pesoActualizado= 0;
            Flecha flechita;
            flechita = flechas.get(0);
            flechas.remove(0);
            for(Flecha flecha : flechas){
                pesoActualizado = pesoActualizado + flecha.getPeso();
            }
            setPeso(PESO_PROPIO + pesoActualizado);
            if(getCantidadFlechas()==0){
                estado = new Vacio();
            }
            return flechita;
        }
        @Override
        public String toString() {
            return " con " + getCantidadFlechas() + " flechas";
        }
    }

    /**
     * La clase LLENO indica el estado del Carcaj con flechas.
     * Debe cambiar al estado CONFLECHAS al quitarse una flecha.
     */
    // TODO - Implementar la clase privada
    private class Lleno extends EstadoContenedor {
        @Override
        public void addElemento(Elemento elemento) throws ContenedorLlenoException, AccionNoPermitidaException {
            // TODO - Implementar metodo
            throw new ContenedorLlenoException("Carcaj lleno");    
        }
        
        @Override
        public Elemento getElemento() throws ContenedorVacioException, AccionNoPermitidaException {
            // TODO - Implementar metodo
            int pesoActualizado;
            pesoActualizado= 0;
            Flecha flechita;
            flechita = flechas.get(0);
            flechas.remove(0);
            for(Flecha flecha : flechas){
                pesoActualizado = pesoActualizado + flecha.getPeso();
            }
            setPeso(PESO_PROPIO + pesoActualizado);
            estado = new ConFlechas();
            return flechita;
        }
        @Override
        public String toString() {
            return " con " + getCantidadFlechas() + " flechas (lleno)";
        }
    }
/*
 ***** FIN Patron State *****
 */

    /**
     * Modifica el peso del carcaj.
     * Puede sumar o restar peso.
     *
     * @param peso El peso a modificar.
     */
    public void addPeso (Integer peso) {
        setPeso(getPeso() + peso);
    }

    /**
     * Devuelve informacion sobre el carcaj de la forma:
     *     "<nombre> <estado>"
     * donde <nombre> es el nombre del carcaj y
     * <estado> es el toString de cada estado.
     */
    @Override
    public String toString() {
        // TODO - Implementar metodo
        return getNombre() + estado.toString();
    }

    public Integer getCantidadFlechas () {
        return flechas.size();
    }

    public Integer getCapacidad () {
        return capacidad;
    }

    public EstadoContenedor getEstado () {
        return estado;
    }

}