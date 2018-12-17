/*
 * Huffman.cpp
 *
 * Name: Michael McDermott
 * Student ID:004923332
 * Email: mmcdermott011@gmail.com
 *
 */

#include <queue>
#include <vector>
#include<string>
#include <iostream>
#include <fstream>
#include <map>
using namespace std;

/*The Huffman class contains a HuffmanNode struct,
 * a priority Queue, and an encode Map. It will read in a file
 * that contains the letters A-Z and a space along with their
 * respective frequencies. It will build a priority queue and
 * a Huffman Tree based on those frequencies. A map is also generated
 * for ease of use when encoding.
 *
 * The encode function will take a string of text and will
 * use the map to output each letters respective binary code.
 *
 * The decode function takes in a string of binary
 * and will traverse the huffman tree to find each letter
 * and returns a string of the decoded message.
 *
 */
class Huffman {

private:
	struct HuffmanNode {
			char data;
			int freq;
			HuffmanNode * left, *right;

			HuffmanNode(char letter, int number) {
				this->data = letter;
				this->freq = number;
				left = right = NULL;
			}
		};

	/*
	 * Needed for the priorityQueue to compare when sorting
	 */
	struct compare {

	    bool operator()(HuffmanNode* l, HuffmanNode* r)

	    {
	        return (l->freq > r->freq);
	    }
	};

	map<char, string> encodeMap;	//once the huffman tree is built and codes are generated, makes it easy to store in a map

public:
	priority_queue<HuffmanNode*, vector<HuffmanNode*>, compare> priorityQueue; // one priorityQueue for generating the codes


		/* Assumes the tree is already built
		 * takes the message to be encoded and searches its characters in a map of codes
		 * that is already generated
		 *
		 */
		string encode(string message){
			string encoded;
			for(int i = 0; i< message.length(); i++) {
				map<char, string>::iterator it ;
				it = encodeMap.find(toupper(message[i]));
				if(it != encodeMap.end()) {
					encoded+=it->second;
				}
			}
			return encoded;
		}

		/*Takes a string and traverses the huffman tree until it lands on a letter, then it will save the letter and start the search over
		 * it will continue doing this until there are no more characters left in the string
		 *
		 */
		string decode(string message) {
			HuffmanNode * top = priorityQueue.top();
			string decodedMessage;
			for(int i = 0; i < message.length() ; i++) {
				if(message[i] == '0') {
					top=top->left;
				}
				else if(message[i] == '1') {
					top = top->right;
				}
				else {
					cout<<"invalid message to decode. must be in binary with no spaces";
				}
				//cout<< message[i]<< " " <<top->data<<endl;
				if(top->data != '$') {
					decodedMessage+=top->data;
					top = priorityQueue.top();
				}
			}
		return decodedMessage;
		}

		/* Takes in a string for a file name,
		 * reads through the file to create nodes with a character and a frequency
		 * Adds the nodes to a Min Priority Queue
		 */
		void readFile(string nameOfFile){
			ifstream inFile;
			string line;
			char letter;
			int frequency;

			inFile.open("freq.txt");
			if(!inFile) {
				cout<<"Unable to open file " << nameOfFile;
				exit(1);
			}
			while(inFile >> line >> frequency) {
				line.erase(remove(line.begin(), line.end(), '\''), line.end());
				letter = line[0];
				if(letter == '_') {
					letter = ' ';				}
				cout<<letter<< " " <<frequency<<endl;
				HuffmanNode *node = new HuffmanNode(letter, frequency);
				priorityQueue.push(node);

			}
			inFile.close();
		}

		/* Builds the huffman tree by popping from a priority queue
		 * Pops two from a queue and creates a parent node with the frequency being the sum of the
		 * two children's frequencies
		 * after generating the new parent node, it assigns the children to it and pushes
		 * it back to the priority queue. The priority queue will sort it on its own.
		 */
		void buildTree(){
			struct HuffmanNode *leftC, *rightC, *parent;
			 while (priorityQueue.size() != 1) {
			        leftC = priorityQueue.top();
			        priorityQueue.pop();
			        rightC = priorityQueue.top();
			        priorityQueue.pop();
			        parent = new HuffmanNode('$', leftC->freq + rightC->freq);
			        parent->left = leftC;
			        parent->right = rightC;
			        priorityQueue.push(parent);
			    }
			 buildMap(priorityQueue.top(), "");
		}

		/*From the huffman tree this builds a map for easy lookup
		 *
		 */
		void buildMap(HuffmanNode * parent, string code){
			if(parent != NULL){
				if(parent->data != '$') {
					encodeMap.insert(pair<char, string> (parent->data, code));
				}
				buildMap(parent->right, code + "1");
				buildMap(parent->left, code + "0");
			}
		}

		/*Calls the recursive printTree function*/
		void printTree(){
			printTree(priorityQueue.top(), "");
		}

		/*outputs the huffman tree by traversing recursively in order*/
		void printTree(HuffmanNode * parent, string name) {
			if(parent == NULL){
				return;
			}
			printTree(parent->right, name + "1");

			if(parent->data != '$') {
				cout << parent->data << ": " << name << " " <<parent->freq <<"\n";
			}
			printTree(parent->left, name + "0");
		}
};






int main(){
	string fileName = "freq.txt";
	string message = "Huffman Tree Decode Test";
	Huffman huff = Huffman();
	huff.readFile(fileName);
	huff.buildTree();
	huff.printTree();
	string codedMessage = huff.encode(message);
	cout<< message << " = " <<codedMessage<<endl;
	string decodedMessage = huff.decode(codedMessage);
	cout << codedMessage << " = " << decodedMessage <<endl;

}
