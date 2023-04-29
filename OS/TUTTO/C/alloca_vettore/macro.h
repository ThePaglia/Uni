#define ALLOCAVETTORE(PTR) do { 			\
	int j;\
 	PTR=(int32_t*)malloc(10*sizeof(int32_t));	\
	if ( PTR != NULL ) { 				\
 			for(j=0;j<10;j++) { 			\
			PTR[j]=-1000+j; 		\
 		} 					\
	} 						\
}while(0)
