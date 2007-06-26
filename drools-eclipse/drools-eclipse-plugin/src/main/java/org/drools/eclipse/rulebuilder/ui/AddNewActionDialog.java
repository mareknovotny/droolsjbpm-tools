package org.drools.eclipse.rulebuilder.ui;

import java.util.List;

import org.drools.brms.client.modeldriven.SuggestionCompletionEngine;
import org.drools.brms.client.modeldriven.brxml.ActionInsertFact;
import org.drools.brms.client.modeldriven.brxml.ActionInsertLogicalFact;
import org.drools.brms.client.modeldriven.brxml.ActionSetField;
import org.drools.brms.client.modeldriven.brxml.ActionUpdateField;
import org.drools.brms.client.modeldriven.brxml.DSLSentence;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * This provides a popup for new RHS action selection.
 * 
 * @author Anton Arhipov
 * @author Ahti Kitsik
 */
public class AddNewActionDialog extends RuleDialog {

    private final FormToolkit toolkit;

    private RuleModeller      modeller;

    public AddNewActionDialog(Shell parent,
                              FormToolkit toolkit,
                              RuleModeller modeller) {
        super( parent,
               "Add a new action",
               "Pick the values from combos and confirm the selection." );
        this.toolkit = toolkit;
        this.modeller = modeller;
    }

    protected Control createDialogArea(final Composite parent) {
        Composite composite = (Composite) super.createDialogArea( parent );
        String heading = "Choose...";

        createValuesOfFieldPart( composite,
                                   heading );

        createModifyFieldPart( composite,
                                 heading );

        String[] facts = getCompletion().getFactTypes();

        createFactAssertionPart( composite,
                                 heading,
                                 facts );

        createFactLogicalAssertionPart( composite,
                                        heading,
                                        facts );

        createDslSentences( composite,
                            heading );

        return composite;
    }

    private void createModifyFieldPart(Composite composite,
                                       String heading) {
        createLabel( composite,
                     "Modify a field on a fact" );
        final Combo factsCombo = new Combo( composite,
                                                 SWT.READ_ONLY );
        factsCombo.add( heading );

        List boundFacts = modeller.getModel().getBoundFacts();

        for ( int i = 0; i < boundFacts.size(); i++ ) {
            factsCombo.add( (String) boundFacts.get( i ) );
        }
        factsCombo.select( 0 );

        factsCombo.addListener( SWT.Selection,
                                     new Listener() {
                                         public void handleEvent(Event event) {
                                             if ( factsCombo.getSelectionIndex() == 0 ) {
                                                 return;
                                             }

                                             modeller.getModel().addRhsItem(new ActionUpdateField(factsCombo.getText()));
                                             
                                             modeller.setDirty( true );
                                             modeller.reloadRhs();
                                             close();
                                         }
                                     } );
        
    }

    private void createDslSentences(Composite composite,
                                    String heading) {
        if ( getCompletion().getDSLActions().length > 0 ) {
            createLabel( composite,
                         "Actions" );

            final Combo dslCombo = new Combo( composite,
                                              SWT.READ_ONLY );
            dslCombo.add( heading );
            for ( int i = 0; i < getCompletion().getDSLActions().length; i++ ) {
                DSLSentence sen = getCompletion().getDSLActions()[i];
                dslCombo.add( sen.toString() );
            }

            dslCombo.select( 0 );

            dslCombo.addListener( SWT.Selection,
                                  new Listener() {
                                      public void handleEvent(Event event) {
                                          if ( dslCombo.getSelectionIndex() == 0 ) {
                                              return;
                                          }

                                          modeller.getModel().addRhsItem( getCompletion().getDSLActions()[dslCombo.getSelectionIndex() - 1] );
                                          modeller.setDirty( true );
                                          modeller.reloadRhs();
                                          close();
                                      }
                                  } );

        }
    }

    private void createFactLogicalAssertionPart(Composite composite,
                                                String heading,
                                                String[] facts) {
        createLabel( composite,
                     "Logically assert a new fact" );
        final Combo factsCombo = createFactsCombo( composite,
                                                   heading,
                                                   facts );
        factsCombo.addListener( SWT.Selection,
                                new Listener() {
                                    public void handleEvent(Event event) {

                                        if ( factsCombo.getSelectionIndex() == 0 ) {
                                            return;
                                        }

                                        modeller.getModel().addRhsItem( new ActionInsertLogicalFact( factsCombo.getText() ) );
                                        modeller.setDirty( true );
                                        modeller.reloadRhs();
                                        close();
                                    }
                                } );
    }

    private void createFactAssertionPart(Composite composite,
                                         String heading,
                                         String[] facts) {
        createLabel( composite,
                     "Assert a new fact" );
        final Combo factsCombo = createFactsCombo( composite,
                                                   heading,
                                                   facts );
        factsCombo.addListener( SWT.Selection,
                                new Listener() {
                                    public void handleEvent(Event event) {
                                        if ( factsCombo.getSelectionIndex() == 0 ) {
                                            return;
                                        }

                                        modeller.getModel().addRhsItem( new ActionInsertFact( factsCombo.getText() ) );
                                        modeller.setDirty( true );
                                        modeller.reloadRhs();
                                        close();
                                    }
                                } );
    }

    private Combo createFactsCombo(Composite composite,
                                   String heading,
                                   String[] facts) {
        Combo factsCombo = new Combo( composite,
                                      SWT.READ_ONLY );
        factsCombo.add( heading );
        for ( int i = 0; i < facts.length; i++ ) {
            factsCombo.add( facts[i] );
        }
        factsCombo.select( 0 );
        return factsCombo;
    }

    private void createValuesOfFieldPart(Composite composite,
                                           String heading) {
        createLabel( composite,
                     "Set the values of a field on" );
        final Combo globalVarsCombo = new Combo( composite,
                                                 SWT.READ_ONLY );
        globalVarsCombo.add( heading );
        
        List boundFacts = modeller.getModel().getBoundFacts();
        
        for ( int i = 0; i < boundFacts.size(); i++ ) {
            globalVarsCombo.add( (String) boundFacts.get( i ) );
        }
        globalVarsCombo.select( 0 );

        globalVarsCombo.addListener( SWT.Selection,
                                     new Listener() {
                                         public void handleEvent(Event event) {
                                             if ( globalVarsCombo.getSelectionIndex() == 0 ) {
                                                 return;
                                             }

                                             modeller.getModel().addRhsItem( new ActionSetField( globalVarsCombo.getText() ) );
                                             modeller.setDirty( true );
                                             modeller.reloadRhs();
                                             close();
                                         }
                                     } );
    }

    public SuggestionCompletionEngine getCompletion() {
        return modeller.getSuggestionCompletionEngine();
    }

}