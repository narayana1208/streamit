#include <math.h>
#include <stdio.h>
	
const int K=2;
const int N=2;
const int Q=2;
const int W=2;
//const int m=N*Q+W-1;
//const int n=K*N;
// can't do the above in c, calculate
// by hand.
const int m=5;
const int n=4;


void Decode(float C[Q][K], float h[W][K], float *r);
void AddSigma(int n,float sigma,float AhA[K*N][K*N],float AhAsig[K*N][K*N]);
void MatchFilt(int m, int n, float *r, float *Ahr, float A[Q*N+W-1][K*N]);
void SelfMult(int m, int n,float A[Q*N+W-1][K*N],float AhA[K*N][K*N]);
void Forw(int n, float *Ahr, float *u, float L[K*N][K*N]);
void Backs(int n, float *v, float *u, float L[K*N][K*N]);
void CompSigma(int n, float *sigma, float *a, float *b);
void DelMat(float B[Q+W-1][K], float A[Q*N+W-1][K*N]);
void chold(float A[Q*N+W-1][K*N],float L[K*N][K*N],int n);
void ConvMat(float C[Q][K], float B[Q+W-1][K], float h[W][K]);
void PrintD(int n, float *d);
void Print2D(int m,int n, float Mat[n][n]);


/** Main loop -- run this multiple times to get numbers **/
doDecode() {
//  	int K;
//  	int N;
//  	int Q;
//  	int W;
//  	K=2;
//  	N=2;
//  	Q=2;
//  	W=2;
	float h[W][K];
	float C[Q][K];
	float  r[Q*N+W-1];    

	int i,j;
	float sum=0;	


	for (i=0;i<N*Q+W-1;i++)
	    r[i]=i;


	for (i=0;i<K;i++) {
	    sum+=1;
	    //sum=sum/7;

	    for (j=0;j<Q;j++){
		sum+=1;
		C[j][i]=sum;
	    }

	    for (j=0;j<W;j++){
		sum++;
		h[j][i]=sum;
	    }
	}

	Decode(C,h,r);
	//raw_test_pass_reg("done");

  return 0;
}

void main() {
  int i;
  for (i =0; i<10000000; i++) {
    doDecode();
  }
}





// This function performes the complete decoding.
void Decode(float C[Q][K], float h[W][K], float *r)
{
  float B[Q+W-1][K];
  float A[Q*N+W-1][K*N];
  float Ahr[K*N];
  float AhA[K*N][K*N];
  float L[K*N][K*N];
  float u[K*N];
  float v[K*N];
  float sigma;
  float AhAsig[n][n];
  
  ConvMat(C,B,h);
 
  DelMat(B,A);
 
  MatchFilt(m,n,r,Ahr,A);

  //PrintD(n,Ahr);
  

  SelfMult(Q*N+W-1,K*N,A,AhA);

  //Print2D(Q*N+W-1,K*N,A);

  chold(AhA,L,n);

  //Print2D(n,n,L);

  Forw(n,Ahr,u,L);

  Backs(n,v,u,L);

  //PrintD(n,Ahr);

  CompSigma(n,&sigma,v,Ahr);
  
  //cout<< sigma<< endl;

  AddSigma(n,sigma,AhA,AhAsig);

  chold(AhAsig,L,n);

  Forw(n,Ahr,u,L);

  Backs(n,v,u,L);

  //PrintD(n,v);
}

  




/* void Print2D(int m,int n, float Mat[n][n]){ */
/*   int i; */
/*   int j; */
/*   for (i=0; i <m;i++) */
/*     for (j=0;j<n;j++){ */
      
/*       cout<<i<<","<<j<<":"<<Mat[i][j] <<endl; */
/*     } */
/* } */




/* //Prints elements of a vector of size n; */

void PrintD(int n, float *d){
  int i;
  for (i=0 ; i <n ;i++){
    printf("%d:%d\n", i, d[i]);
  }
}
  


//Adds sigma to the diagonal elements
void AddSigma(int n,float sigma,float AhA[K*N][K*N],float AhAsig[K*N][K*N])
{
  int i,j;

  for(i=0;i<n;i++)
    for(j=0;j<n;j++)
      {
	if (i==j) {
	  AhAsig[i][j]=AhA[i][j]+sigma;}
	else {AhAsig[i][j]=AhA[i][j];
	}
      }
}
	





// this does the match filtering, clear from its name!
void MatchFilt(int m, int n, float *r, float *Ahr, float A[Q*N+W-1][K*N])
{
  int i,j,k;

  for (i=0;i<n;i++)
    {
      Ahr[i]=0;
      for (j=0; j<m;j++)
	Ahr[i]+=A[j][i]*r[j];
    }
}


// This multiplies the matrix A by itself
void SelfMult(int m, int n,float A[Q*N+W-1][K*N],float AhA[K*N][K*N])
{
  int i,j,k;

  for (i=0; i<n;i++)
    for (j=0; j<=i ;j++)
      {
	AhA[i][j]=0;
	for ( k=0; k<m;k++)
	  AhA[i][j]+=A[k][i]*A[k][j];
	AhA[j][i]=AhA[i][j];
      }
}


// This performs the Forward subtituion
void Forw(int n, float *Ahr, float *u, float L[K*N][K*N])
{
  int i,j;
  float sum;

  for (i=0; i <n; i++){
    sum=0;
    for (j=0; j<i;j++)
      sum+=L[i][j]*u[j];
    u[i]=(Ahr[i]-sum)/L[i][i];
  }
}

// This performes the Back substituiton
void Backs(int n, float *v, float *u, float L[K*N][K*N])
{
  int i,j;
  float sum;
  for (i=n-1;i>=0;i--){
    sum=0;
    for (j=i+1;j<n;j++)
      sum+=L[j][i]*v[j];
    v[i]=(u[i]-sum)/L[i][i];
  }
}


// this function calculates the avarage distance between two vectors
void CompSigma(int n,float *sigma, float *a, float *b)
{
  int i;
  float sum=0;
  for (i=0; i<n;i++)
    sum+=(a[i]-b[i])*(a[i]-b[i]);
  *sigma=sum/n;
}

      




// this funtion performes the convolution of matrix h and matrix C,
// h is a W by K matrix of channel responses,
// B is a W+Q-1 by K matrix of channel responses,
// C is a Q by K matirx of Channel Signitures
void ConvMat(float C[Q][K], float B[Q+W-1][K], float h[W][K])
{
  int i,j,l;

  for (i=0; i < K ; i++){
    for (j=0; j < W+Q-1; j++)
      B[j][i]=0;
    for (j=0;j < W ;j++)
      for (l=0;l<Q;l++)
	B[j+l][i]+=h[l][i]*C[j][i];
    }
}

//B is a Q+W-1 By K matrix
//A is a N*Q+W-1 By N*K matrix
void DelMat(float B[Q+W-1][K], float A[Q*N+W-1][K*N])
{
  int i,j,l;
  for (i=0;i<K*N;i++)
    for (j=0;j<Q*N+W-1;j++)
      A[j][i]=0;
  
  for (i=0; i <K; i++)
    for (j=0; j<N; j++)
      for (l=0; l<Q+W-1; l++)
	A[j*Q+l][i*N+j]=B[l][i];
}

//A is an n by n matrix, so is L
void chold(float A[Q*N+W-1][K*N],float L[K*N][K*N],int n)
{
  int i,j,k;
  float sum;

  for (i=0; i<n;i++) {
    for (j=i;j<n;j++) {
      for (sum=A[i][j],k=i-1;k>=0;k--)
	sum -= L[i][k]*L[j][k];
      if (i==j) {
	L[j][i]=sqrt(sum);
      }
      else {
	L[j][i]=sum/L[i][i];
	L[i][j]=0;
      }
      
    }
  }
}

	  
  
