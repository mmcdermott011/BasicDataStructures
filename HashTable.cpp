/*
 * HashTable.cpp
 *
 * Name: Michael McDermott
 * Student ID:004923332
 * Email: mmcdermott011@gmail.com
 *
 *	Assumptions:
 *		1) This hash table comes with the assumption that unique keys are entered
 *		   If duplicate keys are entered with different associated values,
 *		   a collision will occur. Both sets will be stored in the hash table,
 *		   but when searching with that key, the table will only return the
 *		   first occurrence of that key.
 *
 *		   Keys and values are being stored in an array of linked lists
 *		   using the chaining method
 *		2) This program will not work correctly if a character or string is entered in the ID section
 *
 */

#include <list>
#include <iostream>
#include<string>

using namespace std;

class Student{
public:
	int ID;
	string firstName;
	string lastName;

	Student(){
		firstName = "/";
		lastName = "/";
		ID = 0;
	}
	Student(string fName, string lName, int id ){
		firstName = fName;
		lastName = lName;
		ID = id;
	}

	void displayInfo(){
		cout<<"Name: " << firstName <<" " <<lastName<<endl;
		cout<<"Student ID: " <<ID<<endl;
		cout<<endl;
	}
	int getID(){
		return ID;
	}
	string getFullName(){
		string fullName = firstName + " " + lastName;
		return fullName;
	}

};


template <class V>
class HashTable {

	list<V> *hashTable;
	int tableSize;
public:
	HashTable(int size) {
		tableSize = size;
		hashTable = new list<V>[tableSize];
	}

	//takes a key value and generates the hashcode
	int hashCode(int key){
		int code = key%tableSize;
		return code;
	}

	void insert(int key, V value) {
		hashTable[hashCode(key)].push_back(value);
	}

	void search(int key, V &value) {
		if(!hashTable[hashCode(key)].empty()){
			list<V> choices  = hashTable[hashCode(key)];
			typename std :: list<V> :: iterator ptr = choices.begin();
			bool notFound = true;
			while(notFound){
				if(ptr->getID() == key) {
					notFound = false;
					value = *ptr;
				}
				else{
					ptr ++ ;
				}
			}
		}
	}

	void remove(int key) {
		list<V> choices  = hashTable[hashCode(key)];
			 typename std :: list<V> :: iterator ptr = choices.begin();
				bool notFound = true;
				while(notFound){
					if(ptr->getID() == key) {
						notFound = false;
						cout<<ptr->getID() <<" has been removed"<<endl;
						ptr = choices.erase(ptr);
					}
					else{
						ptr ++ ;
					}
				}
	}
};


Student createStudent(){
	string fName;
	string lName;
	int ID;
	cout<<"Enter Students First Name"<<endl;
	cin >> fName;
	cout<<"Enter Students Last Name"<<endl;
	cin >> lName;
	cout <<"Enter Students ID Number" <<endl;
	cin >> ID;
	Student newStudent = Student(fName, lName, ID);
	return newStudent;
}

int main() {
HashTable<Student> hash = HashTable<Student>(100);
char option;
do {
	cout<< "Choose From The Following: \n";
	cout<< "	A) To Add a Student, Enter A\n";
	cout<< "	B) To Search For A Student, Enter B\n";
	cout<< "	C) To Remove A Student, Enter C\n";
	cout<< "	X) To Exit, Enter X \n";
	cin>>option;
	switch(option){
		case 'A':{
			Student student = createStudent();
			student.displayInfo();
			hash.insert(student.getID(), student);
		}
		break;
		case 'B': {
			int ID;
			cout<<"To Search For A Student, Enter Their Student ID"<<endl;
			cin >> ID;
			Student searchStudent = Student();
			hash.search(ID, searchStudent);
			if(searchStudent.getFullName() == "/ /"){
				cout<<"Student Not Found"<<endl;
			}
			else{
				cout <<"Student ID: " << searchStudent.getID() <<endl;
				cout <<"Student Name: " <<searchStudent.getFullName()<<endl;
			}
		}
			break;
		case 'C': {
			int ID;
			cout<<"To Remove A Student, Enter Their Student ID"<<endl;
			cin >> ID;
			hash.remove(ID);
		}
			break;
		case 'X':
			cout<<"Exiting..."<<endl;
			break;
		default:
			cout<<"Error, enter either A, B, or X"<<endl;
			break;
	}
	cout<<endl;
}while(option != 'X');
return 0;


}

