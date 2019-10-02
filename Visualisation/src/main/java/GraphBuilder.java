import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

import java.awt.*;

import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point2;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.spriteManager.*;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.*;
import java.util.*;


public class GraphBuilder {
    static Graph graph = new MultiGraph("Social Graph");
    //static final Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
    //static final View view = viewer.addDefaultView(false);

    public static void Build(SocialGraph socialGraph, SocialGraph path){
        if (path==null)
        {
            Transformation(socialGraph);
        }
        else {
            Transformation(socialGraph);
            String St= " fill-mode: image-scaled;" +
                    " fill-image: url('C:\\Users\\Anastasia\\IdeaProjects\\SocialGraphBuilder\\node2.png');";
            Integer id;
            for (Node n:graph){
                id=Integer.parseInt(n.toString());
                if (path.getUserById(id) != null) System.out.println(id+""); {

                    n.addAttribute("ui.style", St);
                    Iterable<Edge> edges=n.getEachLeavingEdge();
                    for (Edge ed:edges){
                        if (path.getUserById(Integer.parseInt(ed.getNode1().toString())) != null){
                            ed.addAttribute("ui.style", "fill-color: #002137;");// size: " +String.valueOf(1/zoom)+"px;");
                        }
                    }
                }
            }
            /*for (Edge ed:graph.getEachEdge())
            {
                ed.addAttribute("ui.style", "fill-color: #002137;");// size: " +String.valueOf(1/zoom)+"px;");
            }*/

        }
    }

    public static void Build(SocialGraph socialGraph){
        Build(socialGraph,null);
    }

    public static void Transformation(SocialGraph socialGraph){
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        String styleSheet = WorkWithStyle.read("Style.css");

        ArrayList<User> users = new ArrayList<User>();
        users.addAll(socialGraph.users.values());

        for (int i = 0; i < users.size(); i++) {
            graph.addNode(users.get(i).getId().toString());
            graph.getNode(users.get(i).getId().toString()).addAttribute("ui.label",users.get(i).getFirstName()+" "+users.get(i).getSecondName()+": "+String.valueOf(users.get(i).getVulnerability()));
        }

        String idEdge;
        for (int i = 0; i < users.size(); i++) {
            for (Connection con: socialGraph.getAllConnectionById(users.get(i).getId())) {
                if (con != null) {
                    idEdge=users.get(i).getId().toString()+","+con.getSecondUser().getId().toString();
                    graph.addEdge(idEdge, users.get(i).getId().toString(), con.getSecondUser().getId().toString(),true);
                    if (con.getPossibility()>0)
                        graph.getEdge(idEdge).addAttribute("ui.label",con.getPossibility().toString());
                }
            }
        }
        graph.addAttribute("ui.stylesheet", styleSheet);
        //graph.getNode("A").addAttribute("ui.class", "marked");
        graph.setAutoCreate(true);
        final Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        final View view = viewer.addDefaultView(false);
        view.getCamera().setViewPercent(1);
        ((Component) view).addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                e.consume();
                int i = e.getWheelRotation();
                double factor = Math.pow(1.25, i);
                Camera cam = view.getCamera();
                double zoom = cam.getViewPercent() * factor;
                Point2 pxCenter  = cam.transformGuToPx(cam.getViewCenter().x, cam.getViewCenter().y, 0);
                Point3 guClicked = cam.transformPxToGu(e.getX(), e.getY());
                double newRatioPx2Gu = cam.getMetrics().ratioPx2Gu/factor;
                double x = guClicked.x + (pxCenter.x - e.getX())/newRatioPx2Gu;
                double y = guClicked.y - (pxCenter.y - e.getY())/newRatioPx2Gu;
                String St="size: " + String.valueOf(8/zoom)+"px, "+ String.valueOf(8/zoom)+"px;" +
                        " fill-mode: image-scaled;" +
                        // " fill-image: url('C:\\Users\\Anastasia\\IdeaProjects\\SocialGraphBuilder\\node.png');" +
                        " text-size: "+String.valueOf(3/zoom)+";"+
                        " text-visibility: 0.3;" + " text-alignment: under;" +
                        " text-visibility-mode: under-zoom;";
                for (Node n:graph)
                {
                    //n.removeAttribute("ui.stylesheet");
                    n.addAttribute("ui.style", St);
                    //System.out.println(n.getId());
                }
                for (Edge ed:graph.getEachEdge())                {
                    ed.addAttribute("ui.style", "text-size:"+String.valueOf(3/zoom)+";");// size: " +String.valueOf(1/zoom)+"px;");
                }
                cam.setViewCenter(x, y, 0);
                cam.setViewPercent(zoom);
            }
        });

         /*((Component) view).addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                for (Edge ed: Node.getEdgeToward(e.getID()))
                {
                    ed.addAttribute("ui.style", "text-size:"+String.valueOf(6/zoom)+";");
                }
            }
            public void mouseClicked(MouseEvent e) {

            }
        });*/
        //view.display(,true);


        JFrame socialGraphFrame = new JFrame();
        socialGraphFrame.setTitle("Social Graph");
        socialGraphFrame.setLocationRelativeTo(null);
        socialGraphFrame.setPreferredSize(new Dimension(800, 600));
        socialGraphFrame.pack();
        socialGraphFrame.add((Component) view);
        socialGraphFrame.setVisible(true);

    }

}
