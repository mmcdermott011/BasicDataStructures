//
//  StackApp.cpp
//
//  Name: Michael McDermott
//  Student ID:004923332
//  Email: mmcdermott011@gmail.com
#include <iostream>
#include <sstream>
#include <list>

using namespace std;

template <class T>

class StackApp{
	public:
        int size;

        StackApp() {
        	size = 0;
        	stackList.clear();
        }

       void clear() {
    	   stackList.clear();
       }

       bool isEmpty() {
    	   return stackList.empty();
       }

       void push(T element) {
       	stackList.push_back(element);
       }

       T  pop() {
       	T element = stackList.back();
       	stackList.pop_back();
       	size--;
       	return element;
       }

       T & topEl() {
       	return stackList.back();
       }

       //pops each element and outputs its data to the stack
       void displayAll() {
       	while(!(isEmpty())) {
       		T current = pop();
       		cout << current;
       	}
       }

       //returns the size value of the stack
       int getSize() {
       	return size;
       }

       //takes a pointer to a character array as an expression
       //iterates through the array of characters, evaluating each character as an operator or operand
       //pushes operands to a stack
       //compares operators and performs operations, pushing the result to the stack
       //until only the final result of the expression is left, which is returned as a double
       double evalExp(const char * exp){
        	StackApp<char> Operator = StackApp<char>();
        	StackApp<double> Operand = StackApp<double>();
        	const char *ch = exp;
        	//if it doesn't start with a #, add it to the stack
        	if(*ch!='#') {
        		Operator.push('#');
        	}
        	//first part is converting to postfix notation
        	//second part is evaluating
        	//go through expression and figure out if each token is a number or operator
        	while(*ch!=NULL) {
        		//if the character is just a blank space, move forward one
        		if(*ch == ' ') {
        			ch++;
        		}
        		//if the character is a number, keep going to check if the next char is a number
        		//once you have the whole number, push it to the stack
        		if (isdigit(*ch)){

        			//converting char ASCII value to actual integer, need to subtract 48
        			int operand = *ch - 48;
        			//increment to the next character in the array
        			ch++;

        			//while you havent reached the end of the input and each next character is still a number
        			//combine the numbers into the whole number it represents (allows for numbers larger than a single digit)
        			while(*ch != '\n' && isdigit(*ch)) {
        				int temp = *ch-48;
        				operand = (operand*10) + temp;
        				ch++;
        			}
        			//once you've got the whole number, push it to the operand stack
        			Operand.push(operand);
        			//we're one index too far ahead, so we need to back up one so it can be checked again in the outer loop
        			ch--;
        		}
        		//if its not a number and is an operator, need to figure out its precedence and push it to stack
        		// or pop if necessary
        		else {
        			if(!(Operator.isEmpty())) {
        				switch(opPrec(*ch, Operator.topEl())){
        				//first operator has a higher precedence,
        				//pop operator and two operands, operate, then push the result to operand stack
        				//want to compare the original operator again, so need to back up one, so it moves forward to the same
        				//character when it compares again
        				case '>':{
        					double temp1 = Operand.pop();
        					double temp2 = Operand.pop();
        					Operand.push(operate(temp2,temp1,Operator.pop()));
        					ch--;
        					break;}
        				case '<':
        					Operator.push(*ch);
        					break;
        				case '=':
        					//should only happen when () or ## are being compared, meaning they can both be discarded
        					Operator.pop();
        					break;
        				case ' ':
        					cout<<"err: cannot have "<< *ch<<" & "<< Operator.topEl() <<" next to each other!"<<endl;
        					return 0;
        					break;
        				default:
        					cout<<"err: operator not found"<<endl;
        					return 0;
        					break;
        				}
        			}
        			//if the operator stack is empty, push the operator to it
        			else{
        				Operator.push(*ch);
        				}
        		}

        		ch++;//move to the next char
        	}
        	//once the while loop is all done, there should only be operand left in the top of the stack being the result
        	return Operand.pop();
        }//end of evalExp function

	private:

        list<T> stackList;

        //Takes two characters and returns a characters as a precedence symbol
        //2D array of possibilities hardcoded
        char opPrec(char op2, char op1) {
     	   int row, col;
     	   char precedence[7][7];
     	        precedence[0][0] = '>';
     	      	precedence[0][1] = '>';
             	precedence[0][2] = '<';
  	           	precedence[0][3] = '<';
   	           	precedence[0][4] = '<';
   	           	precedence[0][5] = '>';
     	       	precedence[0][6] = '>';
     	       	precedence[1][0] = '>';
              	precedence[1][1] = '>';
  	           	precedence[1][2] = '<';
  	           	precedence[1][3] = '<';
     	       	precedence[1][4] = '<';
     	       	precedence[1][5] = '>';
              	precedence[1][6] = '>';
   	           	precedence[2][0] = '>';
   	           	precedence[2][1] = '>';
     	       	precedence[2][2] = '>';
     	       	precedence[2][3] = '>';
               	precedence[2][4] = '<';
   	           	precedence[2][5] = '>';
   	           	precedence[2][6] = '>';
   	           	precedence[3][0] = '>';
     	       	precedence[3][1] = '>';
     	       	precedence[3][2] = '>';
              	precedence[3][3] = '>';
   	           	precedence[3][4] = '<';
   	           	precedence[3][5] = '>';
     	       	precedence[3][6] = '>';
     	       	precedence[4][0] = '<';
               	precedence[4][1] = '<';
   	           	precedence[4][2] = '<';
   	           	precedence[4][3] = '<';
   	           	precedence[4][4] = '<';
     	       	precedence[4][5] = '=';
     	       	precedence[4][6] = ' ';
               	precedence[5][0] = '>';
   	           	precedence[5][1] = '>';
   	           	precedence[5][2] = '>';
   	           	precedence[5][3] = '>';
     	       	precedence[5][4] = ' ';
     	       	precedence[5][5] = '>';
               	precedence[5][6] = '>';
   	           	precedence[6][0] = '<';
   	           	precedence[6][1] = '<';
   	           	precedence[6][2] = '<';
     	       	precedence[6][3] = '<';
     	       	precedence[6][4] = '<';
               	precedence[6][5] = ' ';
   	           	precedence[6][6] = '=';
     	        switch(op1) {
     	        case '+':
     	        	row = 0;
     	        	break;
     	        case '-':
     	        	row = 1;
     	            break;
     	        case '*':
     	        	row = 2;
     	            break;
     	        case '/':
     	        	row = 3;
     	            break;
     	        case '(':
     	        	row = 4;
     	            break;
     	        case ')':
     	        	row = 5;
     	            break;
     	        case '#':
         	        row = 6;
         	        break;
     	        default:
     	        	cout<<"err: unsupported operator: " << op1 <<endl;
     	        	exit (1);
     	        	break;
     	        }
     	        switch(op2) {
     	        case '+':
     	           col = 0;
     	           break;
     	        case '-':
     	           col = 1;
     	           break;
     	        case '*':
          	       col = 2;
          	       break;
     	        case '/':
            	   col = 3;
            	   break;
            	case '(':
     	   	       col = 4;
            	   break;
     	        case ')':
         	       col = 5;
         	       break;
                case '#':
             	   col = 6;
             	   break;
     	        }
     	   return precedence[row][col];
        }

        //takes two doubles and a character and performs the equivalent mathematical operation
        //returns the result as a double
        double operate(double op1, double op2, char opnd){
     	   double result = 0;
     	   switch(opnd) {
     	   case '+':
     		   result = op1+op2;
             	break;
     	   case '-':
     		   result = op1-op2;
     	     	break;
     	   case '*':
     		   result = op1*op2;
     		    break;
     	   case '/':
     		   if(op2 != 0) {
     			  result = op1/op2;
     		   }
     		   else{
     			   cout <<"divide by zero error"<<endl;
     			   return 0 ;
     		   }
     		   break;
     	   }
     	   return result;
        }
};


int main () {
	StackApp<char> stack;
	cout<<"---------------Calculator App---------------"<<endl;
	cout<<endl;
	cout<<"Enter math equation followed by a #"<<endl;
	char exp[50];
	cin.getline(exp,50);
	cout << exp << " = "<< stack.evalExp(exp)<<endl;

}

