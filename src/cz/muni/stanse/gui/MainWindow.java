package cz.muni.stanse.gui;

import cz.muni.stanse.Configuration;
import cz.muni.stanse.CheckerConfiguration;
import cz.muni.stanse.SourceConfiguration;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("serial")
public final class MainWindow extends javax.swing.JFrame {

    // public section

    public static MainWindow getInstance() {
        return (mainWindow != null) ? mainWindow :
                                     (mainWindow = new MainWindow());
    }

    public static void setLookAndFeel(final String type) {
        lookAndFeelType = type.toUpperCase();
    }

    
    /**
     * Opens all specified files in GUI, and then sets AllOpenedSourceFiles enumerator.
     * 
     * @param sources
     * @param checkerConf
     */
    public void openSourceFiles(final List<String> sources, Configuration config) {
    	for (String source : sources ) {
    		getOpenedSourceFilesManager().showSourceFile(new java.io.File(source));
    	}
    	// replace sources enumerator by AllOpenedFilesEnumerator
	List<CheckerConfiguration> checkerConf =
		config.getCheckerConfigurations();
    	SourceConfiguration sourceConf = new SourceConfiguration(new AllOpenedFilesEnumerator());
    	setConfiguration(new Configuration(sourceConf, checkerConf));
    }
    
    // package-private section

    Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(final Configuration configuration) {
        this.configuration = configuration;
    }

    ErrorsTreeManager getErrorsTreeManager() {
        return errorsTreeManager;
    }

    OpenedSourceFilesManager getOpenedSourceFilesManager() {
        return openedSourceFilesManager;
    }

    ErrorTracingManager getErrorTracingManager() {
        return errorTracingManager;
    }

    ConsoleManager getConsoleManager() {
        return consoleManager;
    }

    // private section

    private MainWindow() {
        super();

        setLookAndFeel();

        initComponents();

        setIconImage(new javax.swing.ImageIcon(getIconPathName()).getImage());

        configuration = new Configuration();
        errorsTreeManager = new ErrorsTreeManager(errorsTree);
        openedSourceFilesManager = new OpenedSourceFilesManager(
                                                          sourceCodeTabbedPane);
        errorTracingManager = new ErrorTracingManager(gotoFirstNodeButton,
                     gotoNextNodeButton,gotoPrevNodeButton,gotoLastNodeButton,
                     stanseMainMenuItemFirstNode,stanseMainMenuItemNextNode,
                     stanseMainMenuItemPreviousNode,stanseMainMenuItemLastNode);
        consoleManager = new ConsoleManager(ouputConsoleTextArea);

        stanseMainMenuItemExit.addActionListener(
        new java.awt.event.ActionListener() {
            @Override public void actionPerformed(
                                           final java.awt.event.ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
    }

    private static void setLookAndFeel() {
        javax.swing.UIManager.put("swing.boldMetal", Boolean.FALSE);
//        if (lookAndFeelType.toUpperCase().equals("DEFAULT") ||
//            lookAndFeelType.equals("METAL"))
//            return;
        if (lookAndFeelType.toUpperCase().equals("SYSTEM"))
            try {
                javax.swing.UIManager.setLookAndFeel(
                         javax.swing.UIManager.getSystemLookAndFeelClassName());
            } catch (final Exception exception) {
            }
    }

    private static String getIconPathName() {
        return cz.muni.stanse.Stanse.getRootDirectory() + "/stanse_icon.png";
    }

    private Configuration configuration;
    private final ErrorsTreeManager errorsTreeManager;
    private final OpenedSourceFilesManager openedSourceFilesManager;
    private final ErrorTracingManager errorTracingManager;
    private final ConsoleManager consoleManager;

    private static String lookAndFeelType = "DEFAULT";
    private static MainWindow mainWindow = null;

    //
    // Begin --- section generated by NetBeans - do not modify ! ---
    //
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitterSourcesAndErrors = new javax.swing.JSplitPane();
        sourceCodeTabbedPane = new javax.swing.JTabbedPane();
        splitterErrorTreeAndTracing = new javax.swing.JSplitPane();
        errorsTreeScrollPane = new javax.swing.JScrollPane();
        errorsTree = new javax.swing.JTree();
        tracingHolder = new javax.swing.JPanel();
        gotoFirstNodeButton = new javax.swing.JButton();
        gotoPrevNodeButton = new javax.swing.JButton();
        gotoNextNodeButton = new javax.swing.JButton();
        gotoLastNodeButton = new javax.swing.JButton();
        locationInfoScrollPane = new javax.swing.JScrollPane();
        ouputConsoleTextArea = new javax.swing.JTextArea();
        configureButton = new javax.swing.JButton();
        checkButton = new javax.swing.JButton();
        stanseMainMenuBar = new javax.swing.JMenuBar();
        stanseMainMenuFile = new javax.swing.JMenu();
        stanseMainMenuItemFile = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        stanseMainMenuItemCloseActiveTab = new javax.swing.JMenuItem();
        stanseMainMenuItemCloseAllTabs = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        stanseMainMenuItemAST = new javax.swing.JMenuItem();
        statnseMainMenuItemCFG = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        stanseMainMenuItemExit = new javax.swing.JMenuItem();
        stanseMainMenuChecking = new javax.swing.JMenu();
        stanseMainMenuItemCheck = new javax.swing.JMenuItem();
        stanseMainMenuItemConfigure = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        stanseMainMenuItemFirstNode = new javax.swing.JMenuItem();
        stanseMainMenuItemNextNode = new javax.swing.JMenuItem();
        stanseMainMenuItemPreviousNode = new javax.swing.JMenuItem();
        stanseMainMenuItemLastNode = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        stanseMainMenuItemTraceCFG = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Stanse");
        setName("mainFrame"); // NOI18N

        splitterSourcesAndErrors.setDividerLocation(575);
        splitterSourcesAndErrors.setResizeWeight(0.5);
        splitterSourcesAndErrors.setLeftComponent(sourceCodeTabbedPane);

        splitterErrorTreeAndTracing.setDividerLocation(500);
        splitterErrorTreeAndTracing.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        splitterErrorTreeAndTracing.setResizeWeight(0.5);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        errorsTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        errorsTree.setRootVisible(false);
        errorsTreeScrollPane.setViewportView(errorsTree);

        splitterErrorTreeAndTracing.setTopComponent(errorsTreeScrollPane);

        tracingHolder.setFocusable(false);

        gotoFirstNodeButton.setText("First");
        gotoFirstNodeButton.setToolTipText("Go to first error-trace location in source code (here is a source of problem we are tracing)");
        gotoFirstNodeButton.setFocusable(false);

        gotoPrevNodeButton.setText("<<");
        gotoPrevNodeButton.setToolTipText("Go to previous error-trace location in source code");
        gotoPrevNodeButton.setFocusable(false);

        gotoNextNodeButton.setText(">>");
        gotoNextNodeButton.setToolTipText("Go to next error-trace location in source code");
        gotoNextNodeButton.setFocusable(false);

        gotoLastNodeButton.setText("Last");
        gotoLastNodeButton.setToolTipText("Go to last error-trace location in source code (there we are in error state in code)");
        gotoLastNodeButton.setFocusable(false);

        locationInfoScrollPane.setAutoscrolls(true);

        ouputConsoleTextArea.setColumns(20);
        ouputConsoleTextArea.setEditable(false);
        ouputConsoleTextArea.setLineWrap(true);
        ouputConsoleTextArea.setRows(5);
        ouputConsoleTextArea.setWrapStyleWord(true);
        locationInfoScrollPane.setViewportView(ouputConsoleTextArea);

        configureButton.setAction(new ActionConfigure());
        configureButton.setText("Configure");
        configureButton.setToolTipText("Configure checking procedure. Specify which checkers should be performer on which source code files.");
        configureButton.setFocusable(false);

        checkButton.setAction(new ActionCheckForBugs());
        checkButton.setText("!");
        checkButton.setToolTipText("Check for bugs");
        checkButton.setFocusable(false);

        org.jdesktop.layout.GroupLayout tracingHolderLayout = new org.jdesktop.layout.GroupLayout(tracingHolder);
        tracingHolder.setLayout(tracingHolderLayout);
        tracingHolderLayout.setHorizontalGroup(
            tracingHolderLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tracingHolderLayout.createSequentialGroup()
                .add(gotoFirstNodeButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gotoPrevNodeButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gotoNextNodeButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gotoLastNodeButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 451, Short.MAX_VALUE)
                .add(checkButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(configureButton)
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, locationInfoScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
        );
        tracingHolderLayout.setVerticalGroup(
            tracingHolderLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tracingHolderLayout.createSequentialGroup()
                .add(tracingHolderLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(gotoNextNodeButton)
                    .add(gotoPrevNodeButton)
                    .add(gotoFirstNodeButton)
                    .add(gotoLastNodeButton)
                    .add(configureButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(checkButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(locationInfoScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE))
        );

        splitterErrorTreeAndTracing.setBottomComponent(tracingHolder);

        splitterSourcesAndErrors.setRightComponent(splitterErrorTreeAndTracing);

        stanseMainMenuFile.setText("File");

        stanseMainMenuItemFile.setAction(new ActionOpenSourceCodeFile());
        stanseMainMenuItemFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        stanseMainMenuItemFile.setText("Open source file");
        stanseMainMenuItemFile.setToolTipText("Specify the source code file to be checked for bugs");
        stanseMainMenuFile.add(stanseMainMenuItemFile);
        stanseMainMenuFile.add(jSeparator1);

        stanseMainMenuItemCloseActiveTab.setAction(new ActionCloseActiveTab());
        stanseMainMenuItemCloseActiveTab.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        stanseMainMenuItemCloseActiveTab.setText("Close active tab");
        stanseMainMenuItemCloseActiveTab.setToolTipText("Closes curently visible tab with source code file.");
        stanseMainMenuFile.add(stanseMainMenuItemCloseActiveTab);

        stanseMainMenuItemCloseAllTabs.setAction(new ActionCloseAllTabs());
        stanseMainMenuItemCloseAllTabs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        stanseMainMenuItemCloseAllTabs.setText("Close all tabs");
        stanseMainMenuItemCloseAllTabs.setToolTipText("Closes all tabs with source code files.");
        stanseMainMenuFile.add(stanseMainMenuItemCloseAllTabs);
        stanseMainMenuFile.add(jSeparator5);

        stanseMainMenuItemAST.setText("Show Abstract Syntax Trees");
        stanseMainMenuItemAST.setToolTipText("Show Abstract Syntax Trees of all the functions in opened and active source code file.");
        stanseMainMenuItemAST.setEnabled(false);
        stanseMainMenuFile.add(stanseMainMenuItemAST);

        statnseMainMenuItemCFG.setText("Show Control Flow Graphs");
        statnseMainMenuItemCFG.setToolTipText("Show Control Flow Graph of all the functions in opened and active source code file.");
        statnseMainMenuItemCFG.setEnabled(false);
        stanseMainMenuFile.add(statnseMainMenuItemCFG);
        stanseMainMenuFile.add(jSeparator2);

        stanseMainMenuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        stanseMainMenuItemExit.setText("Exit");
        stanseMainMenuItemExit.setToolTipText("End the application");
        stanseMainMenuFile.add(stanseMainMenuItemExit);

        stanseMainMenuBar.add(stanseMainMenuFile);

        stanseMainMenuChecking.setText("Checking");

        stanseMainMenuItemCheck.setAction(new ActionCheckForBugs());
        stanseMainMenuItemCheck.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        stanseMainMenuItemCheck.setText("Check for bugs");
        stanseMainMenuItemCheck.setToolTipText("Perform checking of bugs (with respect to current checking configuration).");
        stanseMainMenuChecking.add(stanseMainMenuItemCheck);

        stanseMainMenuItemConfigure.setAction(new ActionConfigure());
        stanseMainMenuItemConfigure.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        stanseMainMenuItemConfigure.setText("Configure");
        stanseMainMenuItemConfigure.setToolTipText("Configure checking procedure. Specify which checkers should be performer on which source code files.");
        stanseMainMenuChecking.add(stanseMainMenuItemConfigure);
        stanseMainMenuChecking.add(jSeparator3);

        stanseMainMenuItemFirstNode.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_HOME, java.awt.event.InputEvent.ALT_MASK));
        stanseMainMenuItemFirstNode.setText("Go to first trace location");
        stanseMainMenuChecking.add(stanseMainMenuItemFirstNode);

        stanseMainMenuItemNextNode.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_DOWN, java.awt.event.InputEvent.ALT_MASK));
        stanseMainMenuItemNextNode.setText("Goto next trace location");
        stanseMainMenuChecking.add(stanseMainMenuItemNextNode);

        stanseMainMenuItemPreviousNode.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_UP, java.awt.event.InputEvent.ALT_MASK));
        stanseMainMenuItemPreviousNode.setText("Goto previous trace location");
        stanseMainMenuChecking.add(stanseMainMenuItemPreviousNode);

        stanseMainMenuItemLastNode.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_END, java.awt.event.InputEvent.ALT_MASK));
        stanseMainMenuItemLastNode.setText("Goto last trace location");
        stanseMainMenuChecking.add(stanseMainMenuItemLastNode);
        stanseMainMenuChecking.add(jSeparator4);

        stanseMainMenuItemTraceCFG.setText("Show trace in Control Flow Graph");
        stanseMainMenuItemTraceCFG.setEnabled(false);
        stanseMainMenuChecking.add(stanseMainMenuItemTraceCFG);

        stanseMainMenuBar.add(stanseMainMenuChecking);

        setJMenuBar(stanseMainMenuBar);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(splitterSourcesAndErrors, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(splitterSourcesAndErrors, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleDescription("Static checker for ISO C (and GNU C extension) programs");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton checkButton;
    private javax.swing.JButton configureButton;
    private javax.swing.JTree errorsTree;
    private javax.swing.JScrollPane errorsTreeScrollPane;
    private javax.swing.JButton gotoFirstNodeButton;
    private javax.swing.JButton gotoLastNodeButton;
    private javax.swing.JButton gotoNextNodeButton;
    private javax.swing.JButton gotoPrevNodeButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JScrollPane locationInfoScrollPane;
    private javax.swing.JTextArea ouputConsoleTextArea;
    private javax.swing.JTabbedPane sourceCodeTabbedPane;
    private javax.swing.JSplitPane splitterErrorTreeAndTracing;
    private javax.swing.JSplitPane splitterSourcesAndErrors;
    private javax.swing.JMenuBar stanseMainMenuBar;
    private javax.swing.JMenu stanseMainMenuChecking;
    private javax.swing.JMenu stanseMainMenuFile;
    private javax.swing.JMenuItem stanseMainMenuItemAST;
    private javax.swing.JMenuItem stanseMainMenuItemCheck;
    private javax.swing.JMenuItem stanseMainMenuItemCloseActiveTab;
    private javax.swing.JMenuItem stanseMainMenuItemCloseAllTabs;
    private javax.swing.JMenuItem stanseMainMenuItemConfigure;
    private javax.swing.JMenuItem stanseMainMenuItemExit;
    private javax.swing.JMenuItem stanseMainMenuItemFile;
    private javax.swing.JMenuItem stanseMainMenuItemFirstNode;
    private javax.swing.JMenuItem stanseMainMenuItemLastNode;
    private javax.swing.JMenuItem stanseMainMenuItemNextNode;
    private javax.swing.JMenuItem stanseMainMenuItemPreviousNode;
    private javax.swing.JMenuItem stanseMainMenuItemTraceCFG;
    private javax.swing.JMenuItem statnseMainMenuItemCFG;
    private javax.swing.JPanel tracingHolder;
    // End of variables declaration//GEN-END:variables
    //
    // End --- section generated by NetBeans - do not modify ! ---
    //
}
