
.data
  AA:     .space 400  		# int AA[100]
  BB:     .space 400  		# int BB[100]
  CC:     .space 400  		# int CC[100]
  m:      .space 4   		# m is an int whose value is at most 10
                     		# actual size of the above matrices is mxm

      # You may add more variables here if you need to
  i: .space 4
  row: .space 4
  column: .space 4
  
  newline:	.asciiz "\n"
  space:	.asciiz " "

.text
     sw    $0, 4($0)       # for (i = 0;
     lw    $t1, 4($0)       # allocate register for i
 
main:

#------- INSERT YOUR CODE HERE for main -------
#
#  Best is to convert the C program line by line
#    into its assembly equivalent.  Carefully review
#    the coding templates near the end of Lecture 8.
#
#
#  1.  First, read m (the matrices will then be size mxm).
#  2.  Next, read matrix A followed by matrix B.
#  3.  Compute matrix product.  You will need triple-nested loops for this.
#  4.  Print the result, one row per line, with one (or more) space(s) between
#      values within a row.
#  5.  Exit.
#
#------------ END CODE ---------------
addi $v0, $0, 5
syscall
add $s0, $0, $v0 #s0 will contain m
readAAloop: 
 sll $t1, $t0, 2 #compute offset 
 addi $v0, $0, 5 # read input
 syscall
 sw $v0, AA($t1) #store inputted number in AA[i]
 addi $t0, $t0, 1 #i++
 mult $s0, $s0 #m * m
 mflo $t4
 slt $t3, $t0, $t4 #is i less than m * m 
 bne $t3, $0, readAAloop
 add $t0, $0, $0 #reset i
 readBBloop: 
 sll $t1, $t0, 2 #compute offset 
 addi $v0, $0, 5 # read input
 syscall
 sw $v0, BB($t1) #store inputted number in AA[i]
 addi $t0, $t0, 1 #i++
 mult $s0, $s0 #m * m
 mflo $t4
 slt $t3, $t0, $t4 #is i less than m * m 
 bne $t3, $0, readBBloop
  #reset these temporaries to use for row and column
  add $t2, $0, $0 #t2 = row
  add $t3, $0, $0 #t3 = column
 
 multiplyMatrix: 
  add $t0, $0, $0 #reset i
  add $t1, $0, $0 #set t1 to 0 to use for dot product
  forLoop: 
   add $t4, $s0, $0 #store m in t4
   mult $t2, $t4
   mflo $t5 #row * m 
   add $t5, $t5, $t0 # add i 
   sll $t5, $t5, 2 #compute offset 
   lw $v0, AA($t5) # AA[ (row * M) + i] 
   mult $t0, $t4 #i * m 
   mflo $t5 
   add $t5, $t5, $t3 # add column
   sll $t5, $t5, 2
   lw $v1, BB($t5) # BB[ (i * m) + column]
   mult $v0, $v1 # multiply them
   mflo $t5 
   add $t1, $t1, $t5 #add it to dot product 
   addi $t0, $t0, 1 #i++
   slt $t5, $t0, $t4 # is i less than m 
   bne $t5, $0, forLoop
  #back to outer loop
  mult $t2, $t4
  mflo $t5 # row * m 
  add $t5, $t5, $t3 #add column 
  sll $t5, $t5, 2 # compute offset 
  sw $t1, CC($t5) #store dot product in CC
  addi 	$v0, $0, 1  # system call 1 is for printing an integer
  add $a0, $0, $t1 #print the dot product 
  syscall
  addi 	$v0, $0, 4  # system call 4 is for printing a string
  la $a0, space	  # address of areaIs string is in $a0
  syscall   # print the string 
  addi $t5, $t4, -1 #M-1
  beq $t3,$t5,columnIF #if (column == M-1) go to columnIF
  #else :
  addi $t3, $t3, 1 #column + 1 
  j multiplyMatrix #restart
  columnIF: 
    beq $t2,$t5, rowIF #if (column == M-1) go to rowIF
    addi $t2, $t2, 1 # add 1 to row 
    add $t3, $0, $0 # set column to 0 
     				# Print a newline
    addi 	$v0, $0, 4  			# system call 4 is for printing a string
    la 	$a0, newline 			# address of areaIs string is in $a0
    syscall           			# print the string
    j multiplyMatrix  
    rowIF: 
    #end print new line
      				# Print a newline
     addi 	$v0, $0, 4  			# system call 4 is for printing a string
     la 	$a0, newline 			# address of areaIs string is in $a0
     syscall           			# print the string
  
exit:                     # this is code to terminate the program -- don't mess with this!
  addi $v0, $0, 10      	# system call code 10 for exit
  syscall               	# exit the program



#------- If you decide to make other functions, place their code here -------
#
#  You do not have to use helper methods, but you may if you would like to.
#  If you do use them, be sure to do all the proper stack management.
#  For this exercise though, it is easy enough to write all your code
#  within main.
#
#------------ END CODE ---------------
