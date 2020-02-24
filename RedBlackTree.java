import NetworkFlow.NetworkFlow;

public class RedBlackTree
{
    Node root;
    public class Node
    {
        int val;
        boolean isRed;
        Node parent;
        Node leftChild;//non-header did not use this field
        Node rightChild;
    }
    public Node findLeaf(Node curNode, int val)
    {
        Node res;
        if(curNode.val>val)
        {
            if(curNode.leftChild==null)
                return curNode;
            res=findLeaf(curNode.leftChild,val);
        }
        else if(curNode.val<val)
        {
            if(curNode.rightChild==null)
                return curNode;
            res=findLeaf(curNode.rightChild,val);
        }
        else
        {
            System.out.println("the node is already exist");
            res=new Node();
        }
        return res;
    }
    public void insertNode(int val)
    {
        if(root==null)
        {
            Node newNode=new Node();
            newNode.val=val;
            newNode.isRed=false;
            newNode.parent=newNode;
            root=newNode;
            return;
        }
        Node parent=findLeaf(root,val);
        //System.out.println("insert below :"+parent.val);
        Node newNode=new Node();
        newNode.val=val;
        newNode.isRed=true;
        if(parent.val<val)
        {
            parent.rightChild=newNode;
            newNode.parent=parent;
        }
        else
        {
            parent.leftChild=newNode;
            newNode.parent=parent;
        }
        //調整tree
        adjustTree(newNode.parent);
    }
    public void adjustTree(Node curNode)
    {
        if(curNode.leftChild!=null&&curNode.rightChild!=null)
        {
         if(!curNode.isRed)
         {
             if(curNode.leftChild.isRed&&curNode.rightChild.isRed)//ok
             {
                 changeColor(curNode);
                 adjustTree(curNode.parent);
             }
             else if(curNode.rightChild.isRed)
             {
                 rightRotation(curNode);
             }
         }
         else
         {
             if(curNode.leftChild.isRed)
             {
                 if(curNode.parent.val<curNode.val)
                 {
                     leftRotation(curNode);
                     curNode=curNode.parent.parent;
                     rightRotation(curNode);
                     curNode=curNode.parent;
                     changeColor(curNode);
                     //adjustTree(curNode.parent);
                 }
                 else if(curNode.parent.val>curNode.val)
                 {
                     curNode=curNode.parent;
                     leftRotation(curNode);
                     curNode.isRed=true;
                     curNode=curNode.parent;
                     curNode.isRed=false;
                     changeColor(curNode);
                     //System.out.println("last one 1");
                     adjustTree(curNode.parent);
                    // System.out.println("last one 2");
                 }
             }
         }
        }
        else if(curNode.rightChild!=null&&curNode.rightChild.isRed)
        {
            if(!curNode.isRed)//ok
            {
                rightRotation(curNode);
                adjustTree(curNode);
            }
            else//ok
            {
                rightRotation(curNode);
                curNode.parent.isRed=true;
                adjustTree(curNode.parent);
            }

        }
        else if(curNode.leftChild!=null&&curNode.leftChild.isRed&&curNode.isRed)//
        {
            //System.out.println("need to be adjust");
                if(curNode.parent.val<curNode.val)
                {
                   leftRotation(curNode);
                   curNode=curNode.parent.parent;
                   rightRotation(curNode);
                   curNode=curNode.parent;
                   changeColor(curNode);
                   //adjustTree(curNode.parent);
                }
                else if(curNode.parent.val>curNode.val)
                {
                    curNode=curNode.parent;
                    leftRotation(curNode);
                    curNode.isRed=true;
                    curNode=curNode.parent;
                    curNode.isRed=false;
                    changeColor(curNode);
                    adjustTree(curNode.parent);
                }
        }
        else if(curNode.leftChild==null&&curNode.rightChild==null)
        {
           // System.out.println("down");
        }
        else
        {
            //System.out.println("沒考慮到這個情況");
        }
    }
    public void leftRotation(Node curNode)//curNode is parent
    {
        //need to be checked
        Node leftChileNode=curNode.leftChild;
        curNode.leftChild=leftChileNode.rightChild;
        if(leftChileNode.rightChild!=null)
            leftChileNode.rightChild.parent=curNode;

        if(root==curNode)
        {
            root=leftChileNode;
            leftChileNode.parent=leftChileNode;
        }
        else
        {
            if(curNode.parent.val>leftChileNode.val)
            {
                curNode.parent.leftChild=leftChileNode;
                leftChileNode.parent=curNode.parent;
            }
            else if(curNode.parent.val<leftChileNode.val)
            {
                curNode.parent.rightChild=leftChileNode;
                leftChileNode.parent=curNode.parent;
            }
        }

        leftChileNode.rightChild=curNode;
        curNode.parent=leftChileNode;
    }
    public void changeColor(Node curNode)//curNode is parent
    {
        curNode.leftChild.isRed=false;
        curNode.rightChild.isRed=false;
        curNode.isRed=true;
        if(curNode==root)
            curNode.isRed=false;
    }
    public void rightRotation(Node curNode)//curNode is parent
    {
        curNode.isRed=true;
        Node rightChileNode=curNode.rightChild;
        rightChileNode.isRed=false;
        curNode.rightChild=rightChileNode.leftChild;
        if(rightChileNode.leftChild!=null)
            rightChileNode.leftChild.parent=curNode;
        if(root==curNode)
        {
            root=rightChileNode;
            rightChileNode.parent=rightChileNode;
        }
        else
        {
            if(curNode.parent.val>rightChileNode.val)
            {
                curNode.parent.leftChild=rightChileNode;
                rightChileNode.parent=curNode.parent;
            }
            else if(curNode.parent.val<rightChileNode.val)
            {
                curNode.parent.rightChild=rightChileNode;
                rightChileNode.parent=curNode.parent;
            }
        }

        rightChileNode.leftChild=curNode;
        curNode.parent=rightChileNode;
    }

    public void printTree()//VLR
    {
        System.out.println("print the tree:");
        printTree(root);
        System.out.println();
    }
    public void printTree(Node curNode)
    {
        if(curNode==null)
            return;
        if(curNode.isRed)
            System.out.print("R"+curNode.val+", ");
        else
            System.out.print("B"+curNode.val+", ");
        printTree(curNode.leftChild);
        printTree(curNode.rightChild);
    }
    public static void main(String[] args)
    {
        System.out.println("Start the red black tree");
        RedBlackTree RBtree=new RedBlackTree();
        RBtree.insertNode(5);
        RBtree.insertNode(16);
        RBtree.insertNode(22);
        RBtree.insertNode(45);
        RBtree.insertNode(2);
        RBtree.insertNode(10);
        RBtree.insertNode(18);
        RBtree.insertNode(30);
        RBtree.insertNode(50);
        RBtree.insertNode(12);
        RBtree.insertNode(1);
//        RBtree.insertNode(7);
        RBtree.printTree();
        System.out.println(RBtree.root.rightChild.val);
    }
}
