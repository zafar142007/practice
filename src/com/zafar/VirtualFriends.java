package com.zafar;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
  These days, you can do all sorts of things online. For example, you can use various websites to make
 virtual friends. For some people, growing their social network (their friends, their friends’ friends, their
 friends’ friends’ friends, and so on), has become an addictive hobby. Just as some people collect stamps,
 other people collect virtual friends.
 Your task is to observe the interactions on such a website and keep track of the size of each person’s
 network.
 Assume that every friendship is mutual. If Fred is Barney’s friend, then Barney is also Fred’s friend.

 Input
     The first line of input contains one integer specifying the number of test cases to follow. Each test case
     begins with a line containing an integer F, the number of friendships formed, which is no more than
     100 000. Each of the following F lines contains the names of two people who have just become friends,
     separated by a space. A name is a string of 1 to 20 letters (uppercase or lowercase).

 Output
     Whenever a friendship is formed, print a line containing one integer, the number of people in the social
     network of the two people who have just become friends

 Sample Input
 1
 3
 Fred Barney
 Barney Betty
 Betty Wilma
 Sample Output
 2
 3
 4
 * @author zafar
 *
 */
public class VirtualFriends {
	private Map<String, Node> nodes=new HashMap<>();
	private Map<Integer, ConnectedComponent> components=new HashMap<>();
	
	public int addNodes(String node1, String node2){
		Node n1=nodes.get(node1);
		Node n2=nodes.get(node2);
		int count=0;
		if(n1==null && n2==null){ //both are new
			int index=getNextComponentIndex();
			ConnectedComponent comp=new ConnectedComponent(index, 2);
			components.put(index,comp);
			nodes.put(node1, new Node(node1,comp));
			nodes.put(node2, new Node(node2,comp));
			count=2;
		}else if((n1!=null && n2==null) || (n1==null && n2!=null)){ //only one is new
			Node n3, n4;
			String name3, name4;
			if(n1==null){
				n3=n1;
				name3=node1;
				n4=n2;
				name4=node2;
			}else{
				n3=n2;
				name3=node2;
				n4=n1;
				name4=node1;
			}
			//n3 is null and n4 is non null
			ConnectedComponent c=n4.getComponent();
			n3=new Node(name3, c);
			nodes.put(name3, n3);
			c.setCount(c.getCount()+1);	
			count=c.getCount();
		}
		else if(n1!=null && n2!=null){ // both are old
			ConnectedComponent c1=n1.getComponent();
			ConnectedComponent c2=n2.getComponent();
			if(c1.getId()!=c2.getId()){ //join two components
				int count1=c1.getCount();			
				int index=getNextComponentIndex();
	
				c1.setId(index);
				
				int count2=c2.getCount();
				c2.setId(index);
				
				c1.setCount(count1+count2);
				c2.setCount(count1+count2);
				count=count1+count2;
			}else{ //no need to join
				count=c1.getCount();
			}
				
		}
		return count;
		
	}
	private static int componentIndex=0;
	public static synchronized int getNextComponentIndex(){
		return componentIndex++;
	}
	public static void main(String arg[]){
		Scanner scanner=new Scanner(System.in);
		VirtualFriends v=new VirtualFriends();
		int cases=scanner.nextInt();
		int friendships=0,i=0,j=0;
		String s1,s2;
		for (i=0;i<cases;i++){
			friendships=scanner.nextInt();
			for (j=0;j<friendships;j++)
			{
				s1=scanner.next();
				s2=scanner.next();
				System.out.println(v.addNodes(s1, s2));				
			}
		}
	}

}
class Node{
	private String name;
	private ConnectedComponent component;
	public Node(){}
	public Node(String name, ConnectedComponent component){
		this.component=component;
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ConnectedComponent getComponent() {
		return component;
	}
	public void setComponent(ConnectedComponent component) {
		this.component = component;
	}
	
}
class ConnectedComponent{
	private int id;
	private int count=0;
	public ConnectedComponent(){}
	public ConnectedComponent(int n, int i){
		id=n;
		count=i;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}