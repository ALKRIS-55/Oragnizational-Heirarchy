

//import java.io.*; 
//import java.util.*; 

// Tree node
class Node {
  int id;
  int level,ht=1;
  Node boss=null,left=null,right=null,next=null,prev=null;
  Node children=null,childtail=null,successor=null;
  
}


public class OrgHierarchy implements OrgHierarchyInterface{

//root node
Node root;
Node owner;
int s=0;
int max(int x,int y ){
	if(x>=y) return x;
	else return y;
}
//we need ht for Avl tree levels
int ht(Node node){
	if(node==null) return 0;
	else return node.ht;
}
Node lrot(Node node){
	Node y=node.right;
	Node z=y.left;
	y.left=node;
	node.right=z;
	if(ht(node.left)>ht(node.right))
	node.ht=1+ht(node.left);
	else node.ht=1+ht(node.right);
	if(ht(y.left)>ht(y.right))
	y.ht=1+ht(y.left);
	else y.ht=1+ht(y.right);
	return y;
}
Node rrot(Node node){
	Node x=node.left;
	Node z=x.right;
	x.right=node;
	node.left=z;
	if(ht(node.left)>ht(node.right))
	node.ht=1+ht(node.left);
	else node.ht=1+ht(node.right);
	if(ht(x.left)>ht(x.right))
	x.ht=1+ht(x.left);
	else x.ht=1+ht(x.right);
	return x;
}
int balance(Node node){
	if(node==null) return 0;
	int b= (ht(node.left)-ht(node.right));
	return b;
}
Node insert(Node node ,int id,Node ch){
if(node==null) return ch;
if(id<node.id) node.left=insert(node.left, id, ch);
else if(id>node.id) node.right=insert(node.right, id, ch);
else return node;
if(ht(node.left)>ht(node.right))
	node.ht=1+ht(node.left);
	else node.ht=1+ht(node.right);
	int b=balance(node);
	//System.out.println(b+" "+node.ht);
	//System.out.println(owner.id);
	if(b>1&&id<node.left.id) return rrot(node);
	//System.out.println(b);
	if(b<-1&& id>node.right.id) return lrot(node);
    if(b>1&&id>node.left.id) {
		node.left=lrot(node.left);
		return rrot(node);
	}
	if(b<-1&&id<node.right.id){node.right=rrot(node.right); return lrot(node);}
	return node;
}

Node del(Node node,int id){
	if(node==null) return node;
	if(id<node.id) node.left=del(node.left,id);
	else if(id>node.id) node.right=del(node.right,id);
	else{
		if(node.left==null||node.right==null){
			if(node.left==null&&node.right==null){
				node=null;}
				else{
					if(node.left==null) node=node.right;
				else node=node.left;
				}
		}
		else{
			Node t=node.right;
			while(t.left!=null){
				t=t.left;
			 }
			 node.id=t.id;
			 node.boss=t.boss;
			 node.level=t.level;
			 Node q=t.children;
			 while(q!=null) {
				 q.boss=node;
				 q=q.next;
			 }
			 node.children=t.children;
			 node.next=t.next;
			 node.prev=t.prev;
			 if(t.prev!=null)
			 t.prev.next=node;
			else {if(t.boss!=null)
				t.boss.children=node;}
			if(t.next!=null)
			t.next.prev=node;
			else{
				if(t.boss!=null)
				t.boss.childtail=node;
			}
			node.childtail=t.childtail;
			node.successor=t.successor;
			node.right=del(node.right, t.id);
		}
	}   if(node==null) return node;
	if(ht(node.left)>ht(node.right))
	node.ht=1+ht(node.left);
	else node.ht=1+ht(node.right);
	int b=balance(node);
	//int b=0;
		//if(root==null) b=0;
		//else b=ht(root.left)-ht(root.right);
		if(b>1) {
			if(balance(node.left)>=0) return rrot(node);
		    else {
				node.left=lrot(node.left);
				return rrot(node);
			}	
		}
		if(b<-1){
			if(balance(node.right)<=0) return lrot(node);
			else{
				node.right=rrot(node.right);
				return lrot(node);
			}
		}
		return node;
		

}

    

Node findnode(Node root,int id){
if(root==null||root.id==id) return root;
if(id>root.id) return findnode(root.right, id);
else return findnode(root.left, id);

}


public boolean isEmpty(){
	return (root==null);
	//your implementation
	// throw new java.lang.UnsupportedOperationException("Not implemented yet.");	
} 

public int size(){
	if(root==null) return 0;
	return s;
	//your implementation
	 //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}

public int level(int id) throws IllegalIDException{
if(findnode(root, id)!=null) return findnode(root, id).level ;
else throw new IllegalIDException("No such id exists!");
	//your implementation
	// throw new java.lang.UnsupportedOperationException("Not implemented yet.");
} 

public void hireOwner(int id) throws NotEmptyException{
	if(isEmpty()){
	//	System.out.println("hi");
		root=new Node();
	root.id=id;
	root.boss=null;
	root.children=null;
	root.childtail=null;
	root.right=null;
	root.left=null;
	root.next=null;
	root.prev=null;
	root.level=1;
	root.ht=1;
	s++;
	owner =root;
}
else{
	throw new NotEmptyException("Not Empty!");
}

	//your implementation
	 //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}

public void hireEmployee(int id, int bossid) throws IllegalIDException{
	//if(root!=null) System.out.println("hi");
	Node p=findnode(root, bossid);
//	if(p==null) {System.out.println("hi1"+root.id);}
	Node c=findnode(root, id);
	//if(c!=null) System.out.println("hi2");
	if(p==null||c!=null) throw new IllegalIDException("Invalid id");
	else{
		//System.out.println(p.id);
		s++;
		Node ch=new Node();
		ch.id=id;
		ch.boss=p;
		ch.ht=1;
		ch.children=null;
		ch.childtail=null;
		ch.left=null;
		ch.right=null;
		ch.level=p.level+1;
		if(p.children==null) {p.children=ch;
			ch.next=null;
		p.childtail=ch;
	ch.prev=null;}
			//System.out.println("hi");}
		else{
			p.children.prev=ch;
ch.next=p.children;
p.children=ch;
ch.prev=null;

		}
	root=insert(root, id,ch);

	}
//	if(findnode(root, 10)!=null) System.out.println(findnode(root, 10).boss.id);
	//your implementation
	 //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
} 

public void fireEmployee(int id) throws IllegalIDException{
	//System.out.println(root.id+" "+root.left.id+" "+root.right.id+" "+root.ht);
	Node f=findnode(root, id);
	if(f==null||id==owner.id) throw new IllegalIDException("Invalid id!");
	else{
	//	if(owner.children!=null)
	//	System.out.println(owner.children.id);
	//	if(owner.children!=null&& owner.children.next!=null)
	//	System.out.println(owner.children.next.id);
		s--;
		if(f.prev!=null){
			f.prev.next=f.next;
			if(f.next!=null)
			f.next.prev=f.prev;
			f.next=null;
			f.prev=null;}
			else{
				//if(id==30)	System.out.println(findnode(root, 10).children.next.id);
				if(f.next==null) f.boss.children=null;
				else f.boss.children=f.next;
		//	if(id==30)	System.out.println(findnode(root, 10).children.id);
				if(f.next!=null)
			f.next.prev=null;
			f.next=null;
			f.prev=null;
			}
			f.boss=null;
			f.children=null;
			f.childtail=null;
			f.prev=null;
		    f.next=null;
		root=del(root, id);
	//	System.out.println(root.id);
		//if(owner.children!=null)
		//System.out.println(owner.children.id);
		//System.out.println(boss(10));
	}
	//your implementation
 	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}
public void fireEmployee(int id, int manageid) throws IllegalIDException{
	
	Node f=findnode(root, id);
	Node m=findnode(root, manageid);
	if(f==null||m==null||owner.id==id){
		throw new IllegalIDException("Invalid id!");
	}
	else{
		//System.out.println(f.id);
		s--;
		Node z=f.children;
		while(z!=null){
           z.boss=m;
		   z=z.next;
		}
		if(m.children!=null){
f.childtail.next=m.children;
m.children.prev=f.childtail;
m.children=f.children;
f.children=null;}
else{
	m.children=f.children;
	m.childtail=f.childtail;
	f.children=null;
}
if(f.prev!=null){
f.prev.next=f.next;
if(f.next!=null)
f.next.prev=f.prev;
f.next=null;
f.prev=null;}
else{
	f.boss.children=f.next;
	if(f.next!=null)
f.next.prev=null;
f.next=null;
f.prev=null;
}
f.boss=null;
f.children=null;
f.childtail=null;

//System.out.println(id);
root=del(root, id);
//System.out.println(boss(10));
	}
	//your implementation
	// throw new java.lang.UnsupportedOperationException("Not implemented yet.");
} 

public int boss(int id) throws IllegalIDException{
	if(id==owner.id) return -1;
	else {
		Node f=findnode(root, id);
		if(f==null) throw new IllegalIDException("Invalid id!");
		else return f.boss.id;
	}
	//your implementation
	// throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}

public int lowestCommonBoss(int id1, int id2) throws IllegalIDException{
	//System.out.println());
	if(owner.id==id1) return -1;
	if(owner.id==id2) return -1;
Node x1=findnode(root, id1);
Node x2=findnode(root, id2);
if(x1==null||x2==null){
	throw new IllegalIDException("Invalid Id!");
}
else{
//	System.out.println(x1.boss.id+" "+x2.boss.id+" "+x2.boss.boss.id);
Node x=x1.boss;
while(x!=owner){
	x.successor=x1;
	x=x.boss;
}
x.successor=x1;
Node y=x2.boss;
//if(y.boss==null) System.out.println("y");
while(y.successor==null && y.successor!=x1){
	y=y.boss;
}
//System.out.println(y.id);
return y.id;
}


	//your implementation
	//throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}
void together(Node a[],int l ,int c, int r){
//int n1=c-l+1;
//int n2=r-c;
int s1=c-l+1,s2=r-c;
int i,j=0;
Node a1[]=new Node[c-l+1];
Node a2[]=new Node[r-c];
for( i=0;i<c-l+1;i++) a1[i]=a[l+i];
for( i=0;i<r-c;i++) a2[i]=a[c+i+1];
//System.out.println(a1.length);
//System.out.println(a2.length);
//for(i=0;i<a1.length;i++)System.out.println(a1[i].id+" ");
//for(i=0;i<a2.length;i++)System.out.println(a2[i].id+" ");
i=0;
j=0;
int k=l;
while(i<s1&&j<s2){
if(a1[i].id<=a2[j].id){
//	System.out.println(a1[i].id);
	a[k]=a1[i];
	i++;
	k++;
}
else{
//	System.out.println(a2[j].id);
	a[k]=a2[j];
	j++;
	k++;
}
}
while(i<s1){
	a[k]=a1[i];
	i++; k++;
}
while(j<s2){
	a[k]=a2[j];
	j++; k++;
}

//for(i=0;i<a.length;i++)System.out.println("hi "+a[i].id+" ");


}
void sort(Node a[], int l, int r){
	//if(l>=r) return;
	if(l<r){
		//System.out.println(a[0].id+" "+a[1].id+" "+a[2].id);
	//	System.out.println(a.length);
		int c= l+(r-l)/2;
		sort(a, l, c);
		sort(a, c+1, r);
		together(a,l,c,r);
	}
}
public String toString(int id) throws IllegalIDException{
Node t=findnode(root, id);
if(t==null) throw new IllegalIDException("Invalid id!");
else{
  // if(findnode(root, 10).children!=null)
	//	System.out.println(findnode(root, 10).children.id);
	String s="";
	s=s+id;
	
		Node a=t.children;
		int sz1=0;
		while(a!=null){
        sz1 ++;
       a=a.next;
		}
		//if(sz1!=0) s=s+",";
		//System.out.println(sz1);
		Node arr1[]=new Node[sz1];
        a=t.children;
		//if(t.children!=null)
		//System.out.println(t.children.id);
		int i=0;
		while(a!=null){
			arr1[i]=a;
			i++;
		   a=a.next;
			}
		//	System.out.println(sz1+" "+arr1[0].id+" "+arr1[1].id+" "+arr1[2].id);
			int sz2=0;
			while(sz1!=0){
				sort(arr1, 0, sz1-1);
			//	System.out.println("yash");
			for(i=0;i<sz1;i++){
				//if(i==0) s=s+arr1[i].id;
			//	else
			   s=s+" "+arr1[i].id;
			   Node r=arr1[i].children;
			   while(r!=null){
				   sz2++;
                  r=r.next;
			   }
			} //if(sz2!=0)s=s+",";
			Node arr2[]=new Node[sz2];
			int j=0;
			for(i=0;i<sz1;i++){
				Node r=arr1[i].children;
			   while(r!=null){
				   arr2[j]=r;
				   j++;
                  r=r.next;
			   }

			 }
			 sz1=sz2;
			 arr1=arr2;
			 sz2=0;
			}
		
	//if(s.charAt(s.length()-1)==',');
	return s;
}

	//your implementation
	 //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
}


}
