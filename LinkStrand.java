/**
 * @author Bob Qian
 * @author Ethan Yu
 */

public class LinkStrand implements IDnaStrand {
    private class Node{
        String info;
        Node next;

        public Node(String s){
            info = s;
            next = null;
        }
    }

    private Node myFirst, myLast;
    private long mySize;
    private int myAppends;
    private int myIndex;
    private Node myCurrent;
    private int myLocalIndex;

    public LinkStrand(String source) {
        initialize(source);
    }

    public LinkStrand() {
        this("");
    }

    @Override
    public long size() {
        return mySize;
    }

    @Override
    public void initialize(String source) {
        myFirst = new Node(source);
        myLast = myFirst;
        mySize = myFirst.info.length();
        myAppends = 0;
        myIndex = 0;
        myCurrent = myFirst;
        myLocalIndex = 0;
    }

    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);
    }

    @Override
    public IDnaStrand append(String dna) {
        Node dnaNode = new Node(dna);
        this.myLast.next = dnaNode;
        this.myLast = dnaNode;
        mySize += dnaNode.info.length();
        myAppends++;
        return this;
    }

    @Override
    public IDnaStrand reverse() {
        StringBuilder originalFirstInfo = new StringBuilder(this.myFirst.info);
        LinkStrand reversedLinkStrand = new LinkStrand(originalFirstInfo.reverse().toString());
        Node currentNode = myFirst.next;
        while(currentNode != null){
            reversedLinkStrand.frontAppend(currentNode.info);
            currentNode = currentNode.next;
        }
        return reversedLinkStrand;
    }

    private void frontAppend(String nextInfo){
        StringBuilder originalInfo = new StringBuilder(nextInfo);
        Node nodeToAppend = new Node(originalInfo.reverse().toString());
        nodeToAppend.next = this.myFirst;
        this.myFirst = nodeToAppend;
        mySize += nextInfo.length();
    }

    @Override
    public int getAppendCount() {
        return myAppends;
    }

    @Override
    public char charAt(int index) {
        if (index >= this.mySize || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        Node currentNode = null;
        int count = 0;
        int dex = 0;
        if (index < myIndex) {
            this.myIndex = 0;
            this.myLocalIndex = 0;
            currentNode = this.myFirst;
        } else {
            count = this.myIndex;
            dex = this.myLocalIndex;
            currentNode = myCurrent;
        }

        while (count < index) {
            count++;
            dex++;
            if (dex >= currentNode.info.length()) {
                dex = 0;
                currentNode = currentNode.next;
            }
        }
        this.myIndex = index;
        this.myLocalIndex = dex;
        this.myCurrent = currentNode;

        return currentNode.info.charAt(dex);
    }

    public String toString(){
        StringBuilder output = new StringBuilder();
        Node current = this.myFirst;
        while(current != null){
            output.append(current.info);
            current = current.next;
        }
        return output.toString();
    }
}
