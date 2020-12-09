.data 
	pattern:	.space 84 
	newline:	.asciiz "\n"
        
.text 0x3000
main : 
	ori     $sp, $0, 0x3000     # Initialize stack pointer to the top word below .text
                                    # The first value on stack will actually go at 0x2ffc
                                    #   because $sp is decremented first.
    	addi    $fp, $sp, -4        # Set $fp to the start of main's stack frame
    	addi $v0, $0, 5 # read input for N
 	syscall
 	or $a0, $0, $v0 #store N in a0
 	or $a1, $0, $0 #set currentLevel (a1) to 0 
 	la $a2, pattern
 	jal makePatterns 
 end: 
	ori   $v0, $0, 10     # system call 10 for exit
	syscall               # we are out of here.	
 makePatterns:
 #----------------------------------------------------------------#
	addi    $sp, $sp, -8        # Make room on stack for saving $ra and $fp
    	sw      $ra, 4($sp)         # Save $ra
    	sw      $fp, 0($sp)         # Save $fp

    	addi    $fp, $sp, 4         # Set $fp to the start of proc1's stack frame
                                # From now on:
                                #     0($fp) --> $ra's saved value
                                #    -4($fp) --> caller's $fp's saved value	
 	# =============================================================
    	# Save any $sx registers that proc1 will modify
                                # Save any of the $sx registers that proc1 modifies
    	addi    $sp, $sp, -8        # e.g., $s0, $s1, $s2, $s3
    	sw      $s0, 4($sp)         # Save $s0
    	sw	$s1, 0($sp)	    # Save $s1	
    	# =============================================================
    	
    	## MAKE PATTERNS BODY ##
    	add $s0, $a1, $0 #save currentLevel in s0
    	bne $a0,$s0, Lelse #if N and currentLevel are equal, jump to printPattern 
    	add $t0, $0, $0
    	add $s1, $0, $a0 #store N (stored in a0) in s1 
    	loop: #for loop to print pattern
    	sll $t1, $t0, 2 #compute offset with t0 as i 
   	addi $v0, $0, 1  #system call 1 is for printing an integer
   	add $t1, $t1, $a2
   	lw $a0, ($t1)
  	syscall
  	addi $t0, $t0, 1 #i++
 	slt $t1, $t0, $s1 #is i less than N
  	bne $t1, $0, loop
  	#add a new line after you've printed the pattern
    	addi 	$v0, $0, 4  			# system call 4 is for printing a string
    	la 	$a0, newline 			# put newLine in $a0
    	syscall           			# print the string
    	or $a0, $s1, $0 #restore a0
    	j Lendif
    	#else:
    	Lelse:
    	sll $t1, $s0, 2 #shift currentLevel by 4 for the index
    	add $t1, $t1, $a2 #add t1 to the array address
    	sw $0,($t1) #store 0 in pattern[currentLevel]
    	addi $a1, $s0, 1 #currentLevel++
    	jal makePatterns #call makePatterns 
    	li $t0, 1 #load one into t0
    	sll $t1, $s0, 2 #compute offset of saved currentLevel
    	add $t1, $t1, $a2 #add t1 to the array address
    	sw $t0, ($t1) #store 1 in pattern[currentLevel]
    	add $a1, $s0, 1 #set a1 to saved currentLevel+1
    	jal makePatterns #make recursive call to finish else statement
	Lendif:
    	## MAKE PATTERNS BODY END ## 
    	
    	# =============================================================
    	# Restore $sx registers
    	lw  $s0,  -8($fp)           # Restore $s0
    	lw  $s1,  -12($fp)	    # Restore $s1
    	# =============================================================
    	# =============================================================
    	# Restore $fp, $ra, and shrink stack back to how we found it,
    	#   and return to caller.

	return_from_proc1:
    	addi    $sp, $fp, 4     # Restore $sp
  	lw      $ra, 0($fp)     # Restore $ra
   	lw      $fp, -4($fp)    # Restore $fp
    	jr      $ra             # Return from procedure

    	# ============================================================#