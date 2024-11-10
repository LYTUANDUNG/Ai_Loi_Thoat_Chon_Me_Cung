package views;

import models.Point;

public class MapView {
    private MatrixView matrixView;
    private RoadView roadView;

    public MapView(MatrixView matrixView, RoadView roadView) {
        this.matrixView = matrixView;
        this.roadView = roadView;
    }

    public void createMatrix(Point[] points) {
        // Kiểm tra xem matrixView đã được khởi tạo chưa, nếu có thì gọi phương thức paintMatrix
        if (matrixView != null) {
            matrixView.paintMatrix(points); 
        }
    }

    // Phương thức để vẽ đường đi
    public void createRoad(Point[] points) {
        // Kiểm tra xem roadView đã được khởi tạo chưa, nếu có thì gọi phương thức paintRoad
        if (roadView != null) {
            roadView.paintRoad(points); 
        }
    }
}
