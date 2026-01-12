package aquario;

/**
 * Representa uma celula do aquario que pode estar vazia ou conter um peixe
 */
public class Celula {
    private Peixe peixe;
    
    public Celula() {
        this.peixe = null;
    }
    
    public boolean estaVazia() {
        return peixe == null;
    }
    
    public Peixe getPeixe() {
        return peixe;
    }
    
    public void setPeixe(Peixe peixe) {
        this.peixe = peixe;
    }
    
    public void limpar() {
        this.peixe = null;
    }
    
    @Override
    public String toString() {
        if (estaVazia()) {
            return ".";
        }
        return peixe.toString();
    }
}