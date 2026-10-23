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
        int[] t1 = Arrays.stream(traversal1.split("-"))
                 .mapToInt(Integer::parseInt)
                 .toArray();

        int[] t2 = Arrays.stream(traversal2.split("-"))
                 .mapToInt(Integer::parseInt)
                 .toArray();

        int[] t3 = Arrays.stream(traversal3.split("-"))
                 .mapToInt(Integer::parseInt)
                 .toArray();

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

            if (buildTree(t[pre], t[in], t[post], size)) {
                int prev = Integer.MIN_VALUE;
                for (int n : t[in]) {
                    if (n <= prev) continue;
                    prev = n;
                }
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

    public static boolean buildTree(int[] preorder, int[] inorder, int[] postorder, int size) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        if (map.size() != inorder.length) return false;

        TreeNode root;
        try {
            root = buildInPre(map, preorder, 0, preorder.length - 1, 0);
        } catch (RuntimeException e) {
            return false;
        }

        List<Integer> post = new ArrayList<>();
        postorder(root, post);

        for (int i = 0; i < size; i++) {
            if (post.get(i) != postorder[i]) return false;
        }

        return true;
    }

    public static TreeNode buildInPre(Map<Integer, Integer> inorder, int[] preorder, int low, int high, int preorderIdx) {
        if (low > high) {
            return null;
        }
        
        int num = preorder[preorderIdx];
        Integer mid = inorder.get(num);
        if (mid == null || mid < low || mid > high) {
            throw new RuntimeException("Invalid traversal");
        }
        
        TreeNode node = new TreeNode(num);

        int leftIdx = 1 + preorderIdx;
        node.setLeft(buildInPre(inorder, preorder, low, mid-1, leftIdx));
        int rightIdx = 1 + preorderIdx + (mid - low);
        node.setRight(buildInPre(inorder, preorder, mid+1, high, rightIdx));

        return node;
    }

    private static void postorder(TreeNode node, List<Integer> out) {
        if (node == null) return;
        postorder(node.getLeft(), out);
        postorder(node.getRight(), out);
        out.add(node.getVal());
    }
}