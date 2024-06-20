


public class FuntctionCreater{
   //Deep_Neural_Network n;

   public static void main (String[] args){
   
      double learningRate = 0.1;
      int epochs = 3000; //Number of times the entire training dataset is passed through the network
      int batchSize = 50; 
      int trainingCycle = 1;
      int dataNodes = 1;   
      int[] layerNodes = {dataNodes, 10, dataNodes};
      
      Deep_Neural_Network n = new Deep_Neural_Network(layerNodes, learningRate);
      n.set_network_type("regression", "ReLU", "mean squared error");    
      n.randomize_initial_weights = true;     
      n.regularize = false;         
      n.revealLevel = 0;
      n.show = true;
      int dataSize = 5000;
      
      //=======================================================================================CREATE DATA   
      //double points[][] = linearFunction(dataSize);
      double points[][] = quadraticFunction(dataSize);
      //==================================================================================================
      
      double[][][] finalData = new double[points.length][1][dataNodes];
      double[][][] finalDataTargets = new double[points.length][1][dataNodes];
      //System.out.println("LINEAR FUNCTION: y = 0.5x+1.5");
      
      for(int i=0;i<points.length;i++){
         //System.out.println("x: "+points[i][0]+" y: "+points[i][1]);
         finalData[i][0][0] = points[i][0];
         //finalData[i][0][1] = 0;
         finalDataTargets[i][0][0] = points[i][1];
         //finalDataTargets[i][0][1] = 0;
      }
      n.provideData(finalData, finalDataTargets, true);
      
      
      n.setDataSplit(0.9, 0.95);
      n.setTrainingParameters(epochs, batchSize, .9);
      trainNetwork(n, epochs);
      
      /*
      points = quadraticFunction();
      System.out.println("QUADRATIC FUNCTION: y = 0.5(x^2)+1.5x+3");
      for(int i=0;i<points.length;i++){
         System.out.println("x: "+points[i][0]+" y: "+points[i][1]);
      }
      
      points = logisticFunction();
      System.out.println("LOGISTIC FUNCTION: y = 1000/(1+e^(-k(x-50)");
      for(int i=0;i<points.length;i++){
         System.out.println("x: "+points[i][0]+" y: "+points[i][1]);
      }
   */
   }
   
   public static void trainNetwork(Deep_Neural_Network n, int epochs){
      double[][] stoppedEpochResults = new double[1][1];
      for(int e=0;e<epochs;e++){
         System.out.println("\nEpoch: "+e);
         if(e == epochs-1){
            n.revealLevel = 1;
         }
         stoppedEpochResults = n.trainEpoch(e);
         readNetworkResults(stoppedEpochResults, n.networkType);
         if(n.accuracyReached){
            break;
         }           
      }
      
      if(n.accuracyReached){
         n.revealLevel = 1;
         n.regressionTest();
      }
   
   }
   
   public static void readNetworkResults(double[][] ntwkR, String typ){
      System.out.println("currentEpoch_tErrors: "+ntwkR[0][0]);
      System.out.println("vError: "+ntwkR[0][1]);
      System.out.println("tError: "+ntwkR[0][2]);
      System.out.println("avg difference: "+ntwkR[0][3]);
   }
   
   public static double[][] linearFunction(int dataSize){
      double[][] points = new double[dataSize][2];
   
      for(int i=0;i<points.length;i++){
         double x = ((double)i)/dataSize;
         //double x = i;
         points[i][0] = x;
         //y = 0.5x+1.5
         points[i][1] = (0.5*x)+1.5;   
      } 
   
      return points;
   }
   
   public static double[][] quadraticFunction(int dataSize){
      double[][] points = new double[dataSize][2];
   
      for(int i=0;i<points.length;i++){
         points[i][0] = (double)(i)/dataSize;
         //y = 0.5(x^2)+1.5x+3
         //points[i][1] = (0.5*(Math.pow(points[i][0], 2)))+(1.5*points[i][0])+3;
         //y = x^2;
         points[i][1] = Math.pow(points[i][0], 2);   
      } 
   
      return points;
   }
   
   public static double[][] logisticFunction(){
      double[][] points = new double[1000][2];
   
      for(int i=0;i<points.length;i++){
         points[i][0] = (double)(i)/1000;
         //y = 1000/(1+e^(-k(x-sigmoidmidpoint)
         points[i][1] = 1000/(1+(Math.exp((-0.01*(points[i][0]-500)))));   
      } 
   
      return points;
   }
   
   public static double[][] exponentialFunction(){
      double[][] points = new double[1000][2];
   
      for(int i=0;i<points.length;i++){
         points[i][0] = i/1000;
         //y = e^x
         points[i][1] = Math.exp(points[i][0]);   
      } 
   
      return points;
   }
   
}