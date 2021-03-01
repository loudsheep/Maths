package ai.neat.singleAgent;

import java.util.ArrayList;

class NodeGene implements Comparable<NodeGene> {

    public enum TYPE {
        INPUT,
        HIDDEN,
        OUTPUT,
        BIAS
    }

    int number;
    TYPE type;
    float inputSum, outputSum;
    float x, y;

    public ArrayList<Integer> connections = new ArrayList<>();

    public NodeGene(int number, TYPE type) {
        this.number = number;
        setType(type);
    }

    public NodeGene(int number) {
        this(number, null);
    }

    public void activate() {
        if (type == TYPE.INPUT) {
            outputSum = inputSum;
            inputSum = 0;
        } else {
            outputSum = sigmoid(inputSum);
            inputSum = 0;
        }
    }

    float sigmoid(float x) {
        return (float) (1 / (1 + Math.exp(-x)));
    }

    public void setConnections(ArrayList<ConnectionGene> connections) {
        for (ConnectionGene c : connections) {
            if (c.nodeFrom == number) {
                this.connections.add(c.number);
            }
        }
    }

    public void setType(TYPE type) {
        this.type = type;
        if (type == TYPE.INPUT) x = 0.1f;
        else if (type == TYPE.OUTPUT) x = 0.9f;
    }

    public NodeGene clone() {
        NodeGene clone = new NodeGene(number, type);
        return clone;
    }

    @Override
    public int compareTo(NodeGene o) {
        return Float.compare(x, o.x);
    }

    public String toString() {
        return "Node " + type.toString() + " x=" + x + " value=" + outputSum;
    }
}
