// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    // make it private after testing.

    private A1List next; // Next Node
    private A1List prev; // Previous Node

    public A1List(int address, int size, int key) {
        super(address, size, key);
    }

    public A1List() {
        super(-1, -1, -1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1, -1, -1); // Intiate the tail sentinel

        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    // pending doubt of corner case
    public A1List Insert(int address, int size, int key) {

        // do we need to handle that case when current node is the tail node?

        A1List temp = new A1List(address, size, key);
        temp.next = this.next;
        temp.next.prev = temp;
        temp.prev = this;
        this.next = temp;
        return temp;
    }

    // checked
    private boolean match(Dictionary d, A1List node) {
        if (d.key == node.key && d.address == node.address && d.size == node.size)
            return true;
        return false;
    }

    public boolean Delete(Dictionary d) {

        boolean flag = false;

        A1List cur = this.getFirst();

        while (cur.next.next != null) {
            if (match(d, cur)) {
                cur.prev.next = cur.next;
                cur.next.prev = cur.prev;
                flag = true;
            }
            cur = cur.next;
        }
        if (match(d, cur)) {
            cur.prev.next = cur.next;
            cur.next.prev = cur.prev;
            flag = true;
        }
        return flag;
    }

    // checked
    private A1List helperFind(A1List node, int k, boolean exact) {
        // reached sentinel
        if (node.next == null)
            return null;
        // if matched
        else if (exact ? node.key == k : node.key >= k)
            return node;
        // recursed to next node
        return helperFind(node.next, k, exact);
    }

    // checked
    public A1List Find(int k, boolean exact) {
        A1List cur = this.getFirst();
        if (cur == null)
            return null;
        return helperFind(cur, k, exact);
    }

    // checked
    public A1List getFirst() {

        A1List cur = this;

        // empty list case
        if ((cur.next == null && cur.prev.prev == null) || (cur.prev == null && cur.next.next == null))
            return null;

        // cur on header
        if (cur.prev == null) {
            cur = cur.next;
        }
        // cur on trailer or in between
        else {
            while (cur.prev.prev != null) {
                cur = cur.prev;
            }
        }
        return cur;
    }

    // checked
    public A1List getNext() {

        A1List cur = this;

        // if cur on trailer
        if (cur.next == null)
            return null;
        // all other cases
        if (cur.next.next != null) {
            return cur.next;
        }
        // empty list
        return null;
    }

    // checked
    private boolean checkSentinel(A1List node) {
        if ((node.prev == null || node.next == null) && (node.key == -1 && node.address == -1 && node.size == -1))
            return true;
        return false;
    }

    private boolean checkCycle(A1List node, int drn) {

        if (checkSentinel(node))
            return false;

        A1List slow, fast;
        slow = node;
        fast = node;

        while (true) {
            // drn=1 for right, 0 for left
            if (drn == 1) {
                slow = slow.next;
                if (!checkSentinel(fast.next))
                    fast = fast.next.next;
                else
                    return false;
            } else {
                slow = slow.prev;
                if (!checkSentinel(fast.prev))
                    fast = fast.prev.prev;
                else
                    return false;
            }

            if (checkSentinel(slow) || checkSentinel(fast))
                return false;

            if (slow == fast)
                return true;
        }
    }

    public boolean sanity() {

        A1List cur = this;

        // check if loop

        if (checkCycle(cur, 1) || checkCycle(cur, 0))
            return false;

        // check if node.prev.next == node for all nodes

        while (!checkSentinel(cur))
            cur = cur.prev;

        cur = cur.next;
        while (!checkSentinel(cur)) {
            if (cur.next.prev != cur)
                return false;
            cur = cur.next;
        }

        return true;
    }

    // everything below for testing

    public static void main(String[] args) {
        A1List temp = new A1List();
        temp = temp.Insert(1, 1, 1);
        temp = temp.Insert(2, 2, 2);
        temp = temp.Insert(3, 3, 3);
        temp = temp.Insert(4, 4, 4);

        Dictionary item = new A1List(4, 4, 4);
        boolean work = temp.Delete(item);
        System.out.println(work);

        System.out.println(temp.key);

        int count = 0;
        for (A1List d = temp.getFirst(); d != null; d = d.getNext())
            count++;
        System.out.println(count);

        A1List search = temp.Find(2, true);
        System.out.println(search);

        System.out.println(temp.sanity());
    }
}
