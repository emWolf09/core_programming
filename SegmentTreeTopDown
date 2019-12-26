/*
  By- Mahesh Nagarwal
*/

package com.wolf.SegmentTree;

import java.util.HashMap;
import java.util.Map;

public class SegmentTree {
	
	static int tree[];
	static int size;
	static Map<Integer,Integer> mp;
	public static void init(int s){
		size = s;
		s = (int)(Math.pow(2,( Math.ceil( Math.log10(s)/Math.log10(2)))));
		//System.out.println("size is "+s);
		tree = new int[s+s-1];
		
	}
	public static void createSegTree(int []a){
		init(a.length);
		createUtil(a,0,0,a.length-1);
	}
	public static int createUtil(int []a,int p,int l,int r){
		if(l==r){
			tree[p]=a[l];
			//System.out.println(a[l]);
			return tree[p];
		}else{
			int m = l+(r-l)/2;
			int x = createUtil(a, p*2+1, l, m);
			int y = createUtil(a, p*2+2, m+1, r);
			tree[p] = Math.max(x, y);
			return tree[p];
		}
		
	}
	
	public static int queryUtil(int lb,int ub,int p,int l,int r){
		if(l>=lb && r<=ub)return tree[p];
		if(lb>r||ub<l)return Integer.MIN_VALUE;
		else{
			int m = l+(r-l)/2;
			int x = queryUtil(lb,ub, p*2+1, l, m);
			int y = queryUtil(lb,ub, p*2+2, m+1, r);
			return Math.max(x, y);
		}
	}
	
	public static int query(int lb,int ub){
		if(lb>ub)return -1;
		if(ub<0||lb>size-1)return -1;
		return queryUtil(lb,ub,0,0,size-1);
	}
	
	public static void displayUtil(int l,int r,int p){
		if(l==r)System.out.println("for range "+l+" "+r+" ->"+tree[p]);
		else {
			int m  = l+(r-l)/2;
			displayUtil(l, m, p*2+1);
			System.out.println("for range "+l+" "+r+" ->"+tree[p]);
			displayUtil(m+1, r, p*2+2);
		}
	}
	public static void display(){
		displayUtil(0,size-1,0);
	}
	
	public static class TreeNode{
		int val;
		TreeNode left,rigth;
		public TreeNode(int v){
			this.val = v;
		}
	}
	
	public static TreeNode re(int a[],int l,int r){
		if(l>r)return null;
		if(l==r){
			return new TreeNode(a[l]);
		}
		int p = query(l,r);
		int m = mp.get(p);
		TreeNode  temp= new TreeNode(p);
		temp.left = re(a,l,m-1);
		temp.rigth = re(a,m+1,r);
		return temp;
	}
	public static TreeNode constructMaximumBinaryTree(int[] n) {
		createSegTree(n);
        mp = new HashMap<>();
        for(int i=0;i<n.length;i++){
        	mp.put(n[i],i);//to handle duplicate
        }
		return re(n,0,n.length-1);
    }
	
	public static void inorder(TreeNode r){
		if(r==null)return;
		else{
			inorder(r.left);
			System.out.println(r.val);
			inorder(r.rigth);
		}
	}
	public static void main(String [] args) {
		int a[] = new int[]{3,2,1,6,0,5};
		TreeNode r = constructMaximumBinaryTree(a);
		inorder(r);
     } 
		
}
