package ai.neat.singleAgent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

class Genome {
    int inputNodes, outputNodes;
    int nextNode = 0;
    int nextConnection = 0;

    ArrayList<NodeGene> nodes = new ArrayList<>();
    ArrayList<ConnectionGene> connections = new ArrayList<>();

    public Genome(int inputs, int outputs) {
        this.inputNodes = inputs;
        this.outputNodes = outputs;

        float d = 1 / ((float) inputs + 1);
        for (int i = 0; i < inputs; i++) {
            NodeGene nn = new NodeGene(i, NodeGene.TYPE.INPUT);
            nn.y = d * (i + 1);
            nodes.add(nn);
            nextNode++;
        }

        d = 1 / ((float) outputs + 1);
        for (int i = 0; i < outputs; i++) {
            NodeGene nn = new NodeGene(i + inputs, NodeGene.TYPE.OUTPUT);
            nn.y = d * (i + 1);
            nodes.add(nn);
            nextNode++;
        }

        NodeGene n = new NodeGene(nextNode, NodeGene.TYPE.HIDDEN);
        n.x = 0.5f;
        n.y = 0.5f;
        nodes.add(n);

        connections.add(new ConnectionGene(nextConnection, 1, 5, 2));
        nextConnection++;
        connections.add(new ConnectionGene(nextConnection, 5, 2, -.5f));
        nextConnection++;
    }

    private Genome(int inputNodes, int outputNodes, boolean clone) {
        this.inputNodes = inputNodes;
        this.outputNodes = outputNodes;
    }

    public float[] calculate(float[] input) {
        if (input.length != inputNodes) throw new IllegalStateException();
        nodes.sort(Comparator.naturalOrder());

        for (int i = 0; i < inputNodes; i++) {
            getNode(i).inputSum = input[i];
        }

        for (NodeGene node : nodes) {
            node.activate();
            node.setConnections(connections);
            for (int con : node.connections) {
                ConnectionGene c = getConnection(con);
                if (c.isEnabled) {
                    getNode(c.nodeTo).inputSum += node.outputSum * c.weight;
                }
            }
        }

        float[] out = new float[outputNodes];
        for (int i = 0; i < outputNodes; i++) {
            out[i] = getNode(i + inputNodes).outputSum;
        }

        return out;
    }

    private boolean exists(ConnectionGene con) {
        for (ConnectionGene c : connections) {
            if (c.nodeTo == con.nodeTo && c.nodeFrom == con.nodeFrom) return true;
        }
        return false;
    }

    public boolean mutateWeight() {
        if (connections.size() == 0) return false;
        Random r = new Random();
        connections.get(r.nextInt(connections.size())).mutateWeight();
        return true;
    }

    public boolean mutateNode() {
        if (connections.size() == 0) return false;

        Random r = new Random();
        ConnectionGene con = connections.get(r.nextInt(connections.size()));
        con.isEnabled = false;

        NodeGene newNode = new NodeGene(nextNode);
        nextNode++;
        newNode.x = (getNode(con.nodeFrom).x + getNode(con.nodeTo).x) / 2f;
        newNode.setType(NodeGene.TYPE.HIDDEN);

        ConnectionGene con1 = new ConnectionGene(nextConnection, con.nodeFrom, newNode.number, con.weight, con.isEnabled);
        nextConnection++;

        ConnectionGene con2 = new ConnectionGene(nextConnection, newNode.number, con.nodeTo, 1f, true);
        nextConnection++;

        connections.add(con1);
        connections.add(con2);
        nodes.add(newNode);

        return true;
    }

    public boolean mutateConnection() {
        if (nodes.size() == 0) return false;
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            NodeGene n1 = nodes.get(r.nextInt(nodes.size()));
            NodeGene n2 = nodes.get(r.nextInt(nodes.size()));

            if (n1.x == n2.x) continue;

            ConnectionGene newCon;

            if (n1.x < n2.x) {
                newCon = new ConnectionGene(nextConnection, n1.number, n2.number);
            } else {
                newCon = new ConnectionGene(nextConnection, n2.number, n1.number);
            }

            if (exists(newCon)) continue;

            nextConnection++;
            connections.add(newCon);
            return true;
        }

        return false;
    }

    public boolean mutateActive() {
        if (connections.size() == 0) return false;
        Random r = new Random();
        connections.get(r.nextInt(connections.size())).mutateEnabled();
        return true;
    }

    private NodeGene getNode(int number) {
        for (NodeGene n : nodes) {
            if (n.number == number) {
                return n;
            }
        }
        return new NodeGene(-1);
    }

    private ConnectionGene getConnection(int number) {
        for (ConnectionGene c : connections) {
            if (c.number == number) {
                return c;
            }
        }
        return new ConnectionGene(-1, -1, -1);
    }

    public Genome clone() {
        Genome g = new Genome(inputNodes, outputNodes, true);
        g.nextNode = nextNode;
        g.nextConnection = nextConnection;
        for (NodeGene n : nodes) {
            g.nodes.add(n.clone());
        }
        for (ConnectionGene c : connections) {
            g.connections.add(c.clone());
        }

        return g;
    }
}
