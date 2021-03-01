package ai.neat.singleAgent;

public class NEATSingle {
    Genome genome;
    public int input, output;

    public NEATSingle(int input, int output) {
        this.genome = new Genome(input, output);
        this.input = input;
        this.output = output;
    }

    private NEATSingle() {
    }

    public float[] query(float[] input) {
        return genome.calculate(input);
    }

    public boolean mutateWeight() {
        return genome.mutateWeight();
    }

    public boolean mutateNode() {
        return genome.mutateNode();
    }

    public boolean mutateConnection() {
        return genome.mutateConnection();
    }

    public boolean mutateActive() {
        return genome.mutateActive();
    }

    public NEATSingle clone() {
        NEATSingle neat = new NEATSingle();
        neat.input = this.input;
        neat.output = this.output;
        neat.genome = this.genome.clone();
        return neat;
    }
}
