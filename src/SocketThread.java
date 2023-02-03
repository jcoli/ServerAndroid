/*
 * Copyright 2014 Tecnocoli/Jeferson Coli
 * http://www.tecnocoli.com.br 
 * All rights reserved
 */


import com.tecnocoli.timetracker.User;
import com.tecnocoli.timetracker.BeginTransmission;
import com.tecnocoli.timetracker.EndTransmission;
import com.tecnocoli.timetracker.TimeTracker;
import com.tecnocoli.timetracker.ReturnMsg;
import com.tecnocoli.timetracker.Projeto;
import com.tecnocoli.timetracker.Budget;
import com.tecnocoli.timetracker.Locais;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * @Project teste-socket-server
 * @brief Classe CalcSocketThread
 * @author Jeferson Coli jcoli@tecnocoli.com.br
 * @date 05/11/2014
 */
public class SocketThread extends Thread {

    private Socket socket;
    private InputStream input;
    private OutputStream output;
    private ManSocket testeSocket;
    private ObjectInputStream readObject;
    private ObjectOutputStream sendObject;
    private ConnDBTimeTracker connDBTimeTracker;
    private ConnDBProjetos connDBProjetos;
    private String typeConnection = "";
    private Boolean connectionOK = false;
    private String login;
    private List<Projeto> listProjetos = new ArrayList<Projeto>();
    private List<Budget> listBudget = new ArrayList<Budget>();
    private List<Locais> listLocais = new ArrayList<Locais>();

    public SocketThread(ManSocket testeSocket, InputStream input, OutputStream output, Socket socket) {
        this.testeSocket = testeSocket;
        this.input = input;
        this.output = output;
        this.socket = socket;

    }

    @Override
    public void run() {
        int bRet = 0;
        int uRet = 0;
        int eRet = 0;
        int rRet = 0;
        int iProj = 0;
        int iBudget = 0;
        boolean finish = false;

        System.out.println("Run 1");
        try {
            System.out.println("Run 2");
            readObject = new ObjectInputStream(this.input);
            System.out.println("Run 2a");
            System.out.println("Run 2a1 " + this.output.toString());
            sendObject = new ObjectOutputStream(this.output);
            System.out.println("Run 2a2 " + this.sendObject.toString());
            //sendObject = new ObjectOutputStream(this.output);

            System.out.println("Run 3");
            do {
                System.out.println("Run 4");
                Object objectRead = readObject.readObject();
                System.out.println("Run 5");
                if (objectRead instanceof BeginTransmission) {
                    BeginTransmission b = (BeginTransmission) objectRead;
                    bRet = this.treatBeginTransmission(b);

                    if (bRet == 0) {
                        ReturnMsg r = new ReturnMsg();
                        r.setReturnStatus(0);
                        r.setTypeOfConn(this.typeConnection);
                        r.setUserName(b.getUserName());

                        sendObject.writeObject(r);
                        this.connectionOK = true;
                        System.out.println("Run 5a " + r.toString());
                    }

                    if (bRet != 0) {
                        System.out.println("Error object begin");
                        finish = true;
                        this.connectionOK = false;
                    }
                }

                if (this.typeConnection.equals("Time") && this.connectionOK) {
                    System.out.println("Run 61");    
                    if (objectRead instanceof TimeTracker) {
                        System.out.println("Run 6");
                        TimeTracker s = (TimeTracker) objectRead;
                        System.out.println("Run 6a " + s.toString());
                        uRet = this.treatTimeTracker(s);

                        ReturnMsg r = new ReturnMsg();
                        r.setReturnStatus(uRet);
                        sendObject.writeObject(r);
                        System.out.println("Error object timeTracker " + uRet);

                    }

                }

                if ((this.typeConnection.equals("Projetos")) && (this.connectionOK)) {
                    this.treatProjeto();
                    int iP = 0;
                    for (Projeto p : listProjetos) {
                        System.out.println("send Projetos "+p.toString());
                        sendObject.writeObject(p);

                        objectRead = readObject.readObject();
                        //System.out.println("send Projetos 1");
                        if (objectRead instanceof ReturnMsg) {
                            ReturnMsg c = (ReturnMsg) objectRead;
                            rRet = this.treatReturnmsg(c);
                            if (rRet != 0) {

                                EndTransmission e = new EndTransmission();
                                e.setCodeOfError(0);
                                e.setRecObejcts(0);
                                e.setSendObejcts(0);
                                sendObject.writeObject(e);
                                finish = true;
                                break;
                            }
                        } else {
                            EndTransmission e = new EndTransmission();
                            e.setCodeOfError(0);
                            e.setRecObejcts(0);
                            e.setSendObejcts(0);
                            sendObject.writeObject(e);
                            finish = true;
                            break;
                        }
                        //System.out.println("send Projetos 2");
                    }

                    //System.out.println("send budget ");
                    for (Projeto p : listProjetos) {
                        //System.out.println("send budget 1 ");
                        listBudget = treatBudget(p.getId());
                        //System.out.println("send budget 2");
                        for (Budget bud : listBudget) {
                            System.out.println("send budget 3 "+bud.getDescricaoCurta());
                            sendObject.writeObject(bud);
                            objectRead = readObject.readObject();
                            if (objectRead instanceof ReturnMsg) {
                                ReturnMsg c = (ReturnMsg) objectRead;
                                rRet = this.treatReturnmsg(c);
                                if (rRet != 0) {
                                    EndTransmission e = new EndTransmission();
                                    e.setCodeOfError(0);
                                    e.setRecObejcts(0);
                                    e.setSendObejcts(0);
                                    sendObject.writeObject(e);
                                    finish = true;
                                    break;
                                }
                            } else {
                                EndTransmission e = new EndTransmission();
                                e.setCodeOfError(0);
                                e.setRecObejcts(0);
                                e.setSendObejcts(0);
                                sendObject.writeObject(e);
                                finish = true;
                                break;
                            }
                            //System.out.println("send budget 4");
                        }
                        //System.out.println("send budget 5");
                    }
                    
                    this.treatLocais();
                    System.out.println("send Locais ");
                    int iL = 0;
                    for (Locais l : listLocais) {
                        System.out.println("send Locais "+l.toString());
                        sendObject.writeObject(l);

                        objectRead = readObject.readObject();
                        //System.out.println("send Projetos 1");
                        if (objectRead instanceof ReturnMsg) {
                            ReturnMsg c = (ReturnMsg) objectRead;
                            rRet = this.treatReturnmsg(c);
                            if (rRet != 0) {

                                EndTransmission e = new EndTransmission();
                                e.setCodeOfError(0);
                                e.setRecObejcts(0);
                                e.setSendObejcts(0);
                                sendObject.writeObject(e);
                                finish = true;
                                break;
                            }
                        } else {
                            EndTransmission e = new EndTransmission();
                            e.setCodeOfError(0);
                            e.setRecObejcts(0);
                            e.setSendObejcts(0);
                            sendObject.writeObject(e);
                            finish = true;
                            break;
                        }
                        //System.out.println("send Projetos 2");
                    }

                    
                    
                    
                    this.connectionOK = false;

                }

                if (!this.connectionOK) {
                    System.out.println("End connection 5");
                    EndTransmission e = new EndTransmission();
                    e.setCodeOfError(0);
                    e.setRecObejcts(0);
                    e.setSendObejcts(0);
                    sendObject.writeObject(e);
                    finish = true;
                }

                if ((objectRead instanceof EndTransmission) && (this.connectionOK)) {
                    EndTransmission e = (EndTransmission) objectRead;
                    eRet = this.treatEndTransmission(e);
                    if (eRet == 0) {
                        finish = true;
                    }

                }

            } while (!finish && socket.isConnected());

            if (!socket.isConnected()) {
                System.out.println("Lost Connection");
            }

            
            
            System.out.println("closing");
            sendObject.close();
            readObject.close();
            output.close();
            input.close();
            socket.close();
            this.interrupt();

        } catch (IOException e) {
            System.out.println("Falha " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("Falha " + e);
        }

    }
    
    

    private int treatBeginTransmission(BeginTransmission beginTransmission) {
        int ret = 0;
        System.out.println("Begin Transmission " + beginTransmission.getTypeOfConn().toString());
        this.typeConnection = beginTransmission.getTypeOfConn();
        this.login = beginTransmission.getUserName();
        return ret;

    }

    private int treatUser(User user) {
        int ret = 0;
        System.out.println("User " + user.toString());
        return ret;
    }

    private int treatEndTransmission(EndTransmission endTransmission) {
        int ret = 0;
        System.out.println("EndTransmission " + endTransmission.toString());
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("erro " + e.getMessage());
        }

        return ret;
    }

    private int treatTimeTracker(TimeTracker timeTracker) {
        int ret = 0;
        System.out.println("time " + timeTracker.toString());
        connDBTimeTracker = new ConnDBTimeTracker();
        ret = connDBTimeTracker.addTimeTracker(timeTracker);
        System.out.println("<<<<<<<<<<<<<<============================>>>>>>>>>>>>>>>>>>>>>>> "+ret);    
        return ret;
    }

    private List<Projeto> treatProjeto() {
        connDBProjetos = new ConnDBProjetos();
        listProjetos = connDBProjetos.listProjetoUsuario(login);
        
        return listProjetos;
    }

    private List<Budget> treatBudget(int projId) {
        connDBProjetos = new ConnDBProjetos();
        listBudget.clear();
        listBudget = connDBProjetos.listBudget(projId);
        return listBudget;

    }
    
    private List<Locais> treatLocais() {
        connDBProjetos = new ConnDBProjetos();
        listLocais.clear();
        listLocais = connDBProjetos.listLocais();
        return listLocais;

    }

    private int treatReturnmsg(ReturnMsg returnMsg) {
        int ret = 0;
        System.out.println("ReturnMsg " + returnMsg.toString());
        ret = returnMsg.getReturnStatus();
        return ret;

    }
}
