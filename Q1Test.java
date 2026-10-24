import java.util.*;

public class Q1Test {
    public static class TreeNode {
        private int val;
        private TreeNode left, right;

        public TreeNode(int val) { 
            this(val, null, null);
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }
    }

    public static class Stack {
        public int size = 0;
        public ArrayList<TreeNode> stack = new ArrayList<>();

        public void push(TreeNode node) {
            stack.add(node);
            size++;
        }

        public TreeNode pop() {
            TreeNode remove = stack.remove(size - 1);
            size--;
            return remove;
        }

        public TreeNode peek() {
            return stack.get(size - 1);
        }
    }
    
    public static void main(String[] args) {

        String result, traversal1, traversal2, traversal3;

        traversal1 = "1-2";
        traversal2 = "2-1-3";
        traversal3 = "1-3-2";
        result = verify(traversal1, traversal2, traversal3);
          
        System.out.println("Traversal 1 : " + traversal1);
        System.out.println("Traversal 2 : " + traversal2);
        System.out.println("Traversal 3 : " + traversal3);
        System.out.println("Expected Result : Invalid traversals");
        System.out.println("  Actual Result : " + result);
        System.out.println();

        traversal1 = "1-2-3";
        traversal2 = "2-1-3";
        traversal3 = "1-3-2";
        result = verify(traversal1, traversal2, traversal3);
        System.out.println("Traversal 1 : " + traversal1);
        System.out.println("Traversal 2 : " + traversal2);
        System.out.println("Traversal 3 : " + traversal3);
        System.out.println(
                "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
        System.out.println("  Actual Result : " + result);
        System.out.println();

        traversal1 = "1-2-3";
        traversal2 = "3-2-1";
        traversal3 = "2-3-1";
        result = verify(traversal1, traversal2, traversal3);
        System.out.println("Traversal 1 : " + traversal1);
        System.out.println("Traversal 2 : " + traversal2);
        System.out.println("Traversal 3 : " + traversal3);
        System.out.println("Expected Result : Invalid traversals");
        System.out.println("  Actual Result : " + result);
        System.out.println();

        traversal1 = "3-1-2-5-4";
        traversal2 = "2-1-4-5-3";
        traversal3 = "1-2-3-4-5";
        result = verify(traversal1, traversal2, traversal3);
        System.out.println("Traversal 1 : " + traversal1);
        System.out.println("Traversal 2 : " + traversal2);
        System.out.println("Traversal 3 : " + traversal3);
        System.out.println(
                "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
        System.out.println("  Actual Result : " + result);
        System.out.println();

        traversal1 = "10-20-30-40-50";
        traversal2 = "20-10-30-40-50";
        traversal3 = "10-50-40-30-20";
        result = verify(traversal1, traversal2, traversal3);
        System.out.println("Traversal 1 : " + traversal1);
        System.out.println("Traversal 2 : " + traversal2);
        System.out.println("Traversal 3 : " + traversal3);
        System.out.println(
                "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
        System.out.println("  Actual Result : " + result);
        System.out.println();
    } 

    // write your codes here
    public static String verify(String traversal1, String traversal2, String traversal3){
        int[] t1;
        int[] t2;
        int[] t3;

        try {
        t1 = Arrays.stream(traversal1.split("-"))
                 .mapToInt(Integer::parseInt)
                 .toArray();

        t2 = Arrays.stream(traversal2.split("-"))
                 .mapToInt(Integer::parseInt)
                 .toArray();

        t3 = Arrays.stream(traversal3.split("-"))
                 .mapToInt(Integer::parseInt)
                 .toArray();

        } catch (NumberFormatException e) {
            return "Invalid traversals";
        }

        if (t1.length != t2.length || t2.length != t3.length) return "Invalid traversals";
        int size = t1.length;

        int[][] t = {t1, t2, t3};
        int[][] perms = {
            {0, 1, 2}, {0, 2, 1}, {1, 0, 2},
            {1, 2, 0}, {2, 0, 1}, {2, 1, 0}
        };

        for (int[] p : perms) {
            int pre = p[0];
            int in = p[1];
            int post = p[2];
            
            if (!isSorted(t[in])) continue;

            TreeNode root = buildTree(t[pre]);
            if (root == null) continue;

            if (valid(t[in], t[post], root, size)) {
                String[] role = new String[3];
                role[pre] = "Preorder";
                role[in] = "Inorder";
                role[post] = "Postorder";
                return "Traversal 1 - " + role[0]
                    + ", Traversal 2 - " + role[1]
                    + ", Traversal 3 - " + role[2];
            }
        }
        return "Invalid traversals";
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] <= arr[i - 1]) return false;
        }
        return true;
    }

    public static boolean valid(int[] inorder, int[] postorder, TreeNode root, int size) {
        int[] candidateInorder = inorder(root, size);
        for (int i = 0; i < size; i++) {
            if (candidateInorder[i] != inorder[i]) return false;
        }

        int[] candidatePostorder = postorder(root, size);
        for (int i = 0; i < size; i++) {
            if (candidatePostorder[i] != postorder[i]) return false;
        }

        return true;
    }

    public static TreeNode buildTree(int[] preorder) {
        Stack stack = new Stack();

        TreeNode root = new TreeNode(preorder[0]);
        stack.push(root);
        for (int i = 1 ; i < preorder.length; i++) {
            TreeNode node = new TreeNode(preorder[i]);
            TreeNode parent = null;
            while (stack.size > 0 && stack.peek().getVal() < preorder[i]) {
                parent = stack.pop();
            }

            if (parent != null) parent.setRight(node);
            else stack.peek().setLeft(node);

            stack.push(node);
        }

        return root;
    }

    private static int[] inorder(TreeNode root, int size) {
        int[] res = new int[size];
        Stack stack = new Stack();

        TreeNode curr = root;
        for (int i = 0; i < size; i++) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.getLeft();
            }
            curr = stack.pop();
            res[i] = curr.getVal();
            curr = curr.getRight();
        }

        return res;
    }

    private static int[] postorder(TreeNode root, int size) {
        int[] res = new int[size];
        Stack s = new Stack();

        s.push(root);

        for (int i = size - 1; i >= 0; i--) {
            TreeNode n = s.pop();
            res[i] = n.getVal();
            if (n.getLeft() != null) s.push(n.getLeft());
            if (n.getRight() != null) s.push(n.getRight());
        }

        return res;
        
    }
}