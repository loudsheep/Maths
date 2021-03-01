package ai.neat.singleAgent;

class ConnectionGene {
    int number;
    int nodeFrom, nodeTo;
    float weight;
    boolean isEnabled;

    public ConnectionGene(int number, int nodeFrom, int nodeTo, float weight, boolean isEnabled) {
        this.number = number;
        this.nodeFrom = nodeFrom;
        this.nodeTo = nodeTo;
        this.weight = weight;
        this.isEnabled = isEnabled;
    }

    public ConnectionGene(int number, int nodeFrom, int nodeTo, float weight) {
        this(number, nodeFrom, nodeTo, weight, true);
    }

    public ConnectionGene(ConnectionGene c) {
        this(c.number, c.nodeFrom, c.nodeTo, c.weight, c.isEnabled);
    }

    public ConnectionGene(int number, int nodeFrom, int nodeTo) {
        this(number, nodeFrom, nodeTo, 1);
    }

    public ConnectionGene(int number) {
        this(number, -1, -1);
    }

    public void changeWeight(float weight) {
        this.weight = weight;
    }

    public void mutateWeight() {
        this.weight += Math.random() - .5f;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    public void mutateEnabled() {
        setEnabled(!isEnabled);
    }

    public ConnectionGene clone() {
        return new ConnectionGene(this);
    }
}
