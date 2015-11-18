## clust4j
A Java-based set of classification clustering algorithm implementations.

### Dependencies:
 - [Apache commons math](https://commons.apache.org/proper/commons-math/), for use of the `AbstractRealMatrix` and `FastMath` classes.
  - The `commons-math` dependency is included in [dep/](https://github.com/tgsmith61591/clust4j/tree/master/dep/commons-math-3-3.2) and is already in the `.classpath`
 - [Apache log4j](http://logging.apache.org/log4j/2.x/), for use of the Logger class
  - The `log4j` dependency is included in [dep/](https://github.com/tgsmith61591/clust4j/tree/master/dep/apache-log4j-1.2.17) and is already in the `.classpath`.
  - In any `BaseClustererPlanner` class, invoke `.setVerbose(true)` to enable logging. Default logging location is: `/tmp/clust4j-${USERNAME}/clust4jlogs/`


### Example data (for reproducability):
    final int k = 2;
    final Array2DRowRealMatrix mat = new Array2DRowRealMatrix(new double[][] {
        new double[] {0.005,     0.182751,  0.1284},
        new double[] {3.65816,   0.29518,   2.123316},
        new double[] {4.1234,    0.2301,    1.8900002}
    });	

    /* Test data, where necessary */
    final Array2DRowRealMatrix test  = new Array2DRowRealMatrix(new double[][] {
        new double[] {0.01302,   0.0012,   0.06948},
        new double[] {3.01837,   2.2293,   3.94812}
    });
		
    final int[] trainLabels = new int[] {0, 1, 1};
    


### Currently implemented algorithms:
- Partitional algorithms:
  - [*k*-Nearest Neighbor](https://en.wikipedia.org/wiki/K-nearest_neighbors_algorithm), a non-parametric, supervised clustering method used for classification. 

            KNN knn = new KNN(mat, test, trainLabels, new KNN.KNNPlanner(k).setScale(true));
            knn.train();
            final int[] results = knn.getPredictedLabels(); // [0,1]

  - [*k*-Means](https://en.wikipedia.org/wiki/K-means_clustering), an unsupervised clustering method that aims to partition *n* observations into *k* clusters in which each observation belongs to the cluster with the nearest mean (centroid), serving as a prototype of the cluster.

            KMeans km = new KMeans(mat, new KMeans.BaseKCentroidPlanner(k).setScale(true));
            km.train();
            // Returns either [1,0] or [0,1] depending on seed:
            final int[] results = km.getPredictedLabels();

  - [*k*-Medoids](https://en.wikipedia.org/wiki/K-medoids), an unsupervised clustering method that chooses datapoints as centers (medoids or exemplars) and works with an arbitrary matrix of distances between datapoints instead of using the Euclidean norm.

            KMedoids km = new KMedoids(mat, new KMedoids.BaseKCentroidPlanner(k).setScale(true));
            km.train();
            // Returns either [1,0] or [0,1] depending on seed:
            final int[] results = km.getPredictedLabels();

- Hierarchical algorithms:
  - [Agglomerative](https://en.wikipedia.org/wiki/Hierarchical_clustering), a "bottom up" approach: each observation starts in its own cluster, and pairs of clusters are merged as one moves up the hierarchy. Agglomerative clustering is __not__ computationally friendly in how it scales. The agglomerative clustering procedure performs at O(n<sup>2</sup>), but far outperforms its cousin, Divisive Clustering.

            AgglomerativeClusterer a = new AgglomerativeClusterer(mat, new BaseHierarchicalPlanner().setScale(true));
            a.train();
            // Print the tree, where 1 is the root:
            System.out.println(a); // Agglomerative clusterer: {1=<5, 2>, 2=<4, 3>, 3=null, 4=null, 5=null}

### Future implementations:
- Density-based:
  - [DBSCAN](http://www.dbs.ifi.lmu.de/Publikationen/Papers/KDD-96.final.frame.pdf), a density-based clustering algorithm: given a set of points in some space, it groups together points that are closely packed together (points with many nearby neighbors), marking as outliers points that lie alone in low-density regions (whose nearest neighbors are too far away).
- Hierarchical algorithms:
  - [Divisive](https://en.wikipedia.org/wiki/Hierarchical_clustering)*, a "top down" approach: all observations start in one cluster, and splits are performed recursively as one moves down the hierarchy. 

*__Update__ (Nov. 2015): as of now, there are no immediate plans to implement Divisive Clustering. The best estimates for DIANA's (DIvisive ANAlysis) runtime is O(2<sup>n</sup>), as opposed to Agglomerative Clustering's O(n<sup>2</sup>). The only reason for implementing it would, thus, be out of the sake of completeness in the family of Hierarchical Clustering.


### Things to note:
 - The default `AbstractClusterer.BaseClustererPlanner.getScale()` returns `false`. This decision was made in an attempt to mitigate data transformations in instances where the analyst may not expect/desire them.  Note that [normalization *is* recommended](http://datascience.stackexchange.com/questions/6715/is-it-necessary-to-standardize-your-data-before-clustering?newreg=f574bddafe484441a7ba99d0d02b0069) prior to clustering and can be set in any algorithm's respective `Planner` class.  Example on a `KMeans` constructor:


        // With normalization
        new KMeans(mat, new KMeans.BaseKCentroidPlanner(k).setScale(true));
        // Without normalization
        new KMeans(mat, k);
