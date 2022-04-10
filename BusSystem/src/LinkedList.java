import java.lang.Integer;
public class LinkedList<T extends Comparable<T>>{


    private class node
    {
        public final T data;
        public node next;
        public int index;

        private node(T data, node next, int index) {
            this.data = data;
            this.next = next;
            this.index = index;

        }
    }

    private node head, tail;

    public LinkedList()
    {
      head = null;
      tail = null;
    }
    private int indPosition = 0;

    public int getIndPosition()
    {
        return indPosition;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public void insertNode(T data, int index)
    {
        node newNode;

        if(isEmpty()) {
            newNode = new node(data, null, index);
            head = newNode;
            tail = newNode;
        }
        else
        {
            tail.next = new node(data, null, index);
            tail = tail.next;
        }
        indPosition++;
    }

    public int getInd(int pos)
    {
        node currNode = head;
        if(pos == 0)
        {
            return  currNode.index;
        }
        for(int i = 0; i < pos; i++)
        {
            currNode = currNode.next;
        }
        return currNode.next.index;

    }
    public String getData(int pos)
    {
        node currNode = head;
        if(pos == 0)
        {
            return  currNode.data.toString();
        }
        for(int i = 0; i < pos; i++)
        {
            currNode = currNode.next;
        }
        return currNode.next.data.toString();
    }

    public void sort(T type)
    {
        head = Mergesort(type, head);
    }

    public node Mergesort( T type, node currHead)
    {


        if(currHead.next == null)
        {
            return currHead;
        }

        node mid = findMid(currHead);
        node head2 = mid.next;
        mid.next = null;
        node newHead1 = Mergesort(type, currHead);
        node newHead2 = Mergesort(type, head2);

        return merge(newHead1, newHead2, type);
    }
    private node merge(node head1, node head2, T type) {

        node merged = new node(type, null, -1);
        node temp = merged;

        while(head1 != null && head2 != null)
        {
            if(head1.data.compareTo(head2.data) < 0)
            {
                temp.next = head1;
                head1 = head1.next;
            }
            else
            {
                temp.next = head2;
                head2 = head2.next;
            }
            temp = temp.next;
        }
        while(head1 != null)
        {
            temp.next = head1;
            head1 = head1.next;
            temp = temp.next;
        }

        while (head2 != null)
        {
            temp.next= head2;
            head2 = head2.next;
            temp = temp.next;
        }
        return merged.next;
    }

    private node findMid(node currHead) {
        node slow = currHead;
        node fast = currHead.next;
        while(fast != null && fast.next != null)
        {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public Integer[] getIntArray()
    {
        Integer[] array = new Integer[indPosition];
        node  currNode = head;
        for(int i = 0; i < indPosition; i++)
        {
            array[i] = ((Integer) currNode.data);
            currNode = currNode.next;
        }
        return array;
    }

    public String[] getStrArray()
    {
        String[] array = new String[indPosition];
        node currNode = head;
        for(int i = 0; i < indPosition; i++){
            array[i] = currNode.data.toString();
            currNode = currNode.next;
        }
        return array;
    }

}
