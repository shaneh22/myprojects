package main;

import mp.commands.Approach;
import mp.commands.Call;
import mp.commands.Define;
import mp.commands.Fail;
import mp.commands.Move;
import mp.commands.Pass;
import mp.commands.ProceedAll;
import mp.commands.Redo;
import mp.commands.Repeat;
import mp.commands.RotateLeftArm;
import mp.commands.RotateRightArm;
import mp.commands.Say;
import mp.commands.Sleep;
import mp.commands.Thread;
import mp.commands.Undo;
import mp.commands.Wait;
import mp.tokens.End;
import mp.tokens.Minus;
import mp.tokens.Number;
import mp.tokens.Plus;
import mp.tokens.Quote;
import mp.tokens.Start;
import mp.tokens.Token;
import mp.tokens.Word;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
@Tags({"ScannerBean"})
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"ScannedString","Tokens","Errors"})
@EditablePropertyNames({"ScannedString"})
public class ScannerBean implements ScannerI {
	String scannedString="";
	Token[] tokens= new Token[0];
	String errors="";
	public String getScannedString() {
		return scannedString;
	}
	public void setScannedString(String input) {
		errors="";
		scannedString=input;
		scanString(scannedString);
	}
	public int indexOf(String s, char ch, int index) {
		for (int i = index; i < s.length(); i++) {
			if (s.charAt(i) == ch) {
				return i;
			}
		}
		return -1;
	}
	public int indexOfNot(String s, char ch, int index) {
		for (int i = index; i < s.length(); i++) {
			if (s.charAt(i) != ch) {
				return i;
			}
		}
		return -1;
	}
	public Token[] getTokens() {
		return tokens;
	}
	public void scanString(String s) {
		Token[] largeTokens = new Token[s.length()];
		int numTokens=0;
		Token myToken = null;
		s=s+" ";
		int tokenStart = indexOfNot(s, ' ', 0);
		int tokenEnd = 0;
		String token = "";
		boolean isQuote= false;
		boolean isPlus=false;
		boolean isMinus=false;
		boolean isStart=false;
		boolean isEnd=false;
		for (int i = 0; i < s.length(); i++) {
			isQuote=false;
			isPlus=false;
			isMinus=false;
			isStart=false;
			isEnd=false;
			if(tokenStart!=-1) {
				tokenEnd = indexOf(s, ' ', tokenStart);
					token = s.substring(tokenStart, tokenEnd);
					if(token.charAt(0)=='"') {
						isQuote=true;
						if(indexOf(s,'"',tokenStart+1)!=-1) {
							token = s.substring(tokenStart+1,indexOf(s,'"',tokenStart+1));
							tokenEnd=indexOf(s,'"',tokenStart+1);
						}
						else {
							token=s.substring(tokenStart+1,s.length());
							errors=token;
							System.out.println("Error: No Closing Quote: " + errors);
							break;
						}
					}
					else if(token.charAt(0)=='+') {
						isPlus=true;
						token=token.charAt(0)+"";
						tokenEnd=tokenStart;
					}
					else if(token.charAt(0)=='-') {
						isMinus=true;
						token=token.charAt(0)+"";
						tokenEnd=tokenStart;
					}
					else if(token.charAt(0)=='{') {
						isStart=true;
						token=token.charAt(0)+"";
						tokenEnd=tokenStart;
					}
					else if(token.charAt(0)=='}') {
						isEnd=true;
						token=token.charAt(0)+"";
						tokenEnd=tokenStart;
					}
					tokenStart = indexOfNot(s, ' ', tokenEnd + 1); 
				if(!token.equals("")) {
					if(isQuote) {
						myToken= new Quote(token);
					} else if(isNumber(token)) {
						myToken= new Number(token);
					} else if(isPlus) {	
						myToken= new Plus(token);
					} else if(isMinus) {
						myToken= new Minus(token);
					} else if(isStart) {
						myToken= new Start(token);
					} else if(isEnd){
						myToken= new End(token);
					} else if(token.toLowerCase().equals("approach")) {
						myToken= new Approach(token);
					} else if(token.toLowerCase().equals("call")) {
						myToken= new Call(token);
					} else if(token.toLowerCase().equals("define")) {
						myToken= new Define(token);
					} else if(token.toLowerCase().equals("fail")) {
						myToken= new Fail(token);
					} else if(token.toLowerCase().equals("move")) {
						myToken= new Move(token);
					} else if(token.toLowerCase().equals("pass")) {
						myToken= new Pass(token);
					} else if(token.toLowerCase().equals("proceedall")) {
						myToken= new ProceedAll(token);
					} else if(token.toLowerCase().equals("redo")) {
						myToken= new Redo(token);
					} else if(token.toLowerCase().equals("repeat")) {
						myToken= new Repeat(token);
					} else if(token.toLowerCase().equals("rotateleftarm")) {
						myToken= new RotateLeftArm(token);
					} else if(token.toLowerCase().equals("rotaterightarm")) {
						myToken= new RotateRightArm(token);
					} else if(token.toLowerCase().equals("say")) {
						myToken= new Say(token);
					} else if(token.toLowerCase().equals("sleep")) {
						myToken= new Sleep(token);
					} else if(token.toLowerCase().equals("thread")) {
						myToken= new Thread(token);
					} else if(token.toLowerCase().equals("undo")) {
						myToken= new Undo(token);
					} else if(token.toLowerCase().equals("wait")) {
						myToken= new Wait(token);
					} else {
						myToken= new Word(token);
					}
					largeTokens[numTokens]=myToken;
					numTokens++;
				}
			}
		}
		tokens= new Token[numTokens];
		for(int i=0;i<numTokens;i++) {
			tokens[i]=largeTokens[i];
		}
	}
	public boolean isNumber(String token) {
		for(int t=0;t<token.length();t++) {
			if(!Character.isDigit(token.charAt(t))){
				return false;
			}
		}
		return true;
	}
	public String getErrors() {
		return errors;
	}
}
