public class Ville extends AbstractVertex {
    private int niveauDemande;
    private Priority priority;

    public Ville(String name, int id, int x, int y, int niveauDemande, Priority priority) {
        super(name, id, x, y);
        this.niveauDemande = niveauDemande;
        this.priority = priority;
    }


    @Override
    public AbstractVertex getElement() {
        return null;
    }

    public Priority getPriority() {
        return priority;
    }
}
