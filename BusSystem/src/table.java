public class table<T extends  Comparable<T>> {

    private class node
    {
        public final T data;
        public node next;
        public node prev;

        private node(T data, node next, node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
    private class indexNode
    {
        public final int data;
        public node next;
        public indexNode nextInd;

        private indexNode(int data, node next, indexNode nextInd)
        {
            this.data = data;
            this.next = next;
            this.nextInd = nextInd;
        }
    }
    public boolean isEmpty()
    {
        return head == null;
    }
    private indexNode head, tail;
    private int indPosition = 0;
    public int[] getIndArray()
    {
        int[] array = new int[indPosition];
        indexNode currNode = head;
        for(int i = 0; i < indPosition; i++)
        {
            array[i] = currNode.data;
            currNode = currNode.nextInd;
        }
        return  array;
    }

    public String getNodeData(int pos)
    {
        indexNode currNode = head;
        for(int i = 0; i < pos; i++)
        {
            currNode = currNode.nextInd;
        }

        return currNode.next.data.toString();
    }
    public table()
    {
        head = null;
        tail = null;
    }
    public void insertInd(int data)
    {
        indexNode newInd;
        if(isEmpty())
        {
            newInd = new indexNode(data, null, null);
            head = newInd;
        }
        else
        {
            newInd = head;
            while(newInd.nextInd != null)
            {
                newInd = newInd.nextInd;
            }
            newInd.nextInd = new indexNode(data, null, null);
        }

        indPosition++;

    }
    public void insertNode(int pos, T data)
    {
        node newNode = null;
        indexNode newInd = head;
        while(newInd.next != null)
        {
            newNode = newInd.next;
            newInd = newInd.nextInd;
        }
        newInd.next = new node(data, null, newNode);
    }

    public void printTable()
    {

        indexNode tempInd = head;


        for(int i = 1; i < indPosition; i++)
        {
            System.out.print(tempInd.data + " ");
            node tempNode = tempInd.next;
            for(int j = 0; j < 8; j++)
            {
                System.out.print(tempNode.data+" ");
                tempNode = tempNode.next;
            }
            System.out.println();
            tempInd = tempInd.nextInd;
        }

    }
    public void test()
    {
        indexNode tempInd = head;
        while(tempInd.nextInd != null)
        {
            System.out.println(tempInd.next.data);
            tempInd = tempInd.nextInd;
        }
        System.out.println(tempInd.next.data);
    }
    public void sort()
    {
        head = Mergesort(head);
    }
    public indexNode Mergesort(indexNode currHead)
    {


        if(currHead.nextInd == null)
        {
            return currHead;
        }

        indexNode mid = findMid(currHead);
        indexNode head2 = mid.nextInd;
        mid.nextInd = null;
        indexNode newHead1 = Mergesort(currHead);
        indexNode newHead2 = Mergesort(head2);

        return merge(newHead1, newHead2);
    }
    private indexNode merge(indexNode head1, indexNode head2) {

        indexNode merged = new indexNode(-1, null, null);
        indexNode temp = merged;

        while(head1 != null && head2 != null)
        {
            if(head1.data < head2.data)
            {
                temp.nextInd = head1;
                head1 = head1.nextInd;
            }
            else
            {
                temp.nextInd = head2;
                head2 = head2.nextInd;
            }
            temp = temp.nextInd;
        }
        while(head1 != null)
        {
            temp.nextInd = head1;
            head1 = head1.nextInd;
            temp = temp.nextInd;
        }

        while (head2 != null)
        {
            temp.nextInd= head2;
            head2 = head2.nextInd;
            temp = temp.nextInd;
        }
        return merged.nextInd;
    }
    private indexNode findMid(indexNode currHead) {
        indexNode slow = currHead;
        indexNode fast = currHead.nextInd;
        while(fast != null && fast.nextInd != null)
        {
            slow = slow.nextInd;
            fast = fast.nextInd.nextInd;
        }
        return slow;
    }
}
