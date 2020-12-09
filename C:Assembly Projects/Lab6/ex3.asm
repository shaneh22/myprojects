#---------------------------------
# Lab 6: Pixel Conversion
#
# Name: Shane Hoffman
#
# --------------------------------
# Below is the expected output.
# 
# Converting pixels to grayscale:
# 0
# 1
# 2
# 34
# 5
# 67
# 89
# Finished.
# -- program is finished running --
#---------------------------------

.data 0x0
  startString:  .asciiz "Converting pixels to grayscale:\n"
  finishString: .asciiz "Finished.\n"
  newline:      .asciiz "\n"
  pixels:       .word   0x00010000, 0x010101, 0x6,      0x3333,
                        0x030c,     0x700853, 0x294999, -1
.text 0x3000

main:
  addi $v0, $0, 4       	# system call code 4 for printing a string
  la   $a0, startString      	# put address of startString in $a0
  syscall               	# print the string
  add $t1, $0, $0 # i is set to 0 in t1 
  add $t2, $0, $0 # rgb is set to 0 in t2 

#------- INSERT YOUR CODE HERE -------
#
# Read and understand the C version of the program, then convert
#   it to MIPS assembly.  The following gives you step-by-step
#   details on how to do it.
#
# First, make sure you have configured MARS under Settings as follows:
#   "Memory Configuration->Compact, Data at Address 0"
#   "Permit extended (pseudo) instructions and formats" should be checked
#
# Write a loop that reads the array pixels using "lw",
#   one word at a time, until a -1 is encountered, which
#   indicates the end of the array.
#
# Each pixel value is a word, of the form 0x00RRGGBB,
#   so your program here should take the rightmost 2 hexits (8 bits)
#   and use that as the blue value, the next 2 hexits as green, and
#   the next 2 hexits as red.  The leftmost 2 hexits are not needed,
#   and will be zero.
#
# Hint:  Use the andi instruction to extract relevant bits
#   from the pixel value.  For example, andi $2, $3, 0x0000000F
#   extracts that rightmost hexit of $3 and places it in $2.
#   This is because ANDing a bit with 1 keeps the bit value, but
#   ANDing a bit with 0 turns it to 0.
#
# After extracting the R, G and B values into $a0, $a1 and $a2,
#   you must then call the procedure rgb_to_gray below using
#   "jal rgb_to_gray", which will compute and return the gray
#   value.
#
# Your code should then print this gray value on the terminal,
#   before moving on to the next pixel value.  For printing,
#   you will need to use a MIPS system call available in MARS.
#   Specifically, it is syscall 1.  The code to print an integer,
#   say the value in $8, is like this:
#
#     addi $v0, $0, 1  // put 1 in $v0 to indicate which syscall
#     add  $a0, $0, $8 // put value to be printed in $a0
#     syscall          // and then execute "syscall"
#
# Continue looping through the array "pixels", until a -1 is
#   encountered, at which point you should exit your loop.
#
# There is already code below that prints the final message "Finished.",
#   and terminate the program.
#
#
#------------ END CODE ---------------
loop:
  sll $t2, $t1, 2 
  lw $t2, pixels($t2) #load the pixels(i) into $t2
  beq $t2, -1, exit #branch if equal to -1
  andi $a2, $t2, 0xff 
  srl $t2, $t2, 8
  andi $a1, $t2, 0xff 
  srl $t2, $t2, 8
  andi $a0, $t2, 0xff
  jal rgb_to_gray 
  add $t3,$0,$v0 # set grey value to t3
  
  addi $v0, $0, 1 #print an int
  add  $a0, $0, $t3 #put grey value in a0
  syscall
  
    				# Print a newline
  addi 	$v0, $0, 4  			# system call 4 is for printing a string
  la 	$a0, newline 			# address of areaIs string is in $a0
  syscall           			# print the string
  
  addi $t1, $t1, 1 #increment i
  j loop # }  
exit:

  addi $v0, $0, 4            	# system call code 4 for printing a string
  la   $a0, finishString   	# put address of finishString in $a0
  syscall               	# print the string

  addi $v0, $0, 10      	# system call code 10 for exit
  syscall               	# exit the program



#----------------------------------------------------------#



#---- Procedure rgb_to_gray ----#
#
#
#-------------------------------#

rgb_to_gray:            # procedure to calculate gray = (red + green + blue) / 3
                        # red is in $a0, green is in $a1, blue is in $a2
                        # gray should be computed in $v0 (return value)
                        # there is no need to use a stack

#------- INSERT YOUR CODE HERE -------
#
# Simply add instructions here to calculate
#   gray = (red + green + blue) / 3
#
#   i.e., $v0 = ($a0 + $a1 + $a2) / 3
add $v0, $a0, $a1
add $v0, $v0, $a2 
div $v0, $v0, 3
#
#  That's it!
#
#------------ END CODE ---------------

  jr $ra                # return to main