.data
cantidad:	.word 10
arreglo:	.word 2, -4, 6, -5, 10, 0, 3, -7, 9, -1
CONTROL:	.word32 0x10000
DATA:		.word32 0x10008

.text
daddi	$sp, $0, 0x400
lwu		$s0, CONTROL($0)
lwu		$s1, DATA($0)
daddi	$a0, $0, arreglo
ld		$a1, cantidad($0)
jal		recorrer
halt

recorrer:	daddi	$sp, $sp, -16
			sd		$ra, 0($sp)
bucle:		sd		$a0, 8($sp)
			ld		$a0, 0($a0)
			jal		leer_num
			ld		$a0, 8($sp)
			daddi	$a1, $a1, -1
			daddi	$a0, $a0, 8
			bnez	$a1, bucle
			ld		$ra, 0($sp)
			daddi	$sp, $sp, 16
			jr		$ra
			
leer_num:	slti	$t0, $a0, 0
			daddi	$t1, $0, 2
			beqz	$t0, terminar
			sd		$a0, 0($s1)
			sd		$t1, 0($s0)
terminar:	jr		$ra