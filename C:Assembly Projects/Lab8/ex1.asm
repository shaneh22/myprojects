.data 
	newline:	.asciiz "\n"
        
.text 0x3000

main:
	ori     $sp, $0, 0x3000     # Initialize stack pointer to the top word below .text
                                    # The first value on stack will actually go at 0x2ffc
                                    #   because $sp is decremented first.
    	addi    $fp, $sp, -4        # Set $fp to the start of main's stack frame

	#----------------------------------------------------------------#
	# Write code here to do exactly what main does in the C program.
	#
	# Please follow these guidelines:
	#
	#	Use syscall 5 each time to read an integer (scanf("%d", ...))
	#	Then call NchooseK to compute the function
	#	Then use syscall 1 to print the result
	#   Put all of the above inside a loop
	#----------------------------------------------------------------#
    	addi $v0, $0, 5 # read input for n
 	syscall
 	or $a0, $0, $v0 #store n in a0
 	beq $v0, $0, end #end the program if a0 is 0
 	addi $v0, $0, 5 # read input for k
 	syscall 
 	or $a1, $0, $v0 #store k in a1
	jal NchooseK #call n choose k 
	#when nChooseK returns, the result is stored in v0
	add $a0, $0, $v0 #put the result in the first argument
	addi $v0, $0, 1 #use syscall 1 to print the result
	syscall
	# Print a newline
    	addi 	$v0, $0, 4  			# system call 4 is for printing a string
    	la 	$a0, newline 			# address of areaIs string is in $a0
    	syscall           			# print the string
	j main
	
end: 
	ori   $v0, $0, 10     # system call 10 for exit
	syscall               # we are out of here.



NchooseK:    		# PLEASE DO NOT CHANGE THE NAME "NchooseK"
	#----------------------------------------------------------------#
	# $a0 has the number n, $a1 has k, from which to compute n choose k
	#
	# Write code here to implement the function you wrote in C.
	# Your implementation MUST be recursive; an iterative
	# implementation is not acceptable.
	#
	# $v0 should have the NchooseK result to be returned to main.
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
    	addi    $sp, $sp, -12        # e.g., $s0, $s1, $s2, $s3
    	sw      $s0, 8($sp)         # Save $s0
	sw	$s1, 4($sp)	    # Save $s1 
	sw 	$s2, 0($sp)	    # Save $s2 
                                # From now on:
                                #    -8($fp) --> $s0's saved value
    	# =============================================================
	
	## NCHOOOSEK BODY ##
	li $v0, 1 #base case return 1 
	beq $a1, $0, return_from_proc1  #if k == 0 return 1
	beq $a0, $a1, return_from_proc1 #if n == k return 1
	#else return nChooseK(n-1,k) + NchooseK(n-1, k-1)
	addi $a0, $a0, -1 # set a0 (n) to n-1 
	add $s0, $0, $a0 #save n-1 into s0 
	addi $s1, $a1, -1 #save k-1 into s1 
	jal NchooseK #call nChooseK(n-1,k)
	#now $v0 has nChooseK(n-1,k)
	add $s2, $0, $v0 #save the value in s2
	or $a0, $0, $s0 #set a0 to n-1 
	or $a1, $0, $s1 #set a1 to k-1
	jal NchooseK #call nChooseK(n-1,k-1)
	#now $v0 has nChooseK(n-1,k-1)
	add $v0, $s2, $v0 #set v0 to nChooseK(n-1,k) + nChooseK(n-1,k-1)
	## END NCHOOSEK BODY ##
	
    	# =============================================================
    	# Restore $sx registers
    	lw  $s0,  -8($fp)           # Restore $s0
    	lw  $s1,  -12($fp)	    # Restore $s1 
    	lw  $s2,  -16($fp)          # Restore $s3
    	# =============================================================
	
   	# =============================================================
    	# Restore $fp, $ra, and shrink stack back to how we found it,
    	#   and return to caller.

	return_from_proc1:
    	addi    $sp, $fp, 4     # Restore $sp
  	lw      $ra, 0($fp)     # Restore $ra
   	lw      $fp, -4($fp)    # Restore $fp
    	jr      $ra             # Return from procedure

    	# =============================================================