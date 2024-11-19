import { create } from 'zustand';

type MedicationStandardType = {
  standardFields: { id: number }[];
  addStandardField: () => void;
  deleteStandardField: () => void;
  setStandardField: (id: number, field: string, value: number) => void;
  resetStandardField: () => void;
};

export const useMedicationStandardStore = create<MedicationStandardType>(
  (set) => ({
    standardFields: [{ id: 0 }],
    addStandardField: () => {
      set((state) => ({
        standardFields: [
          ...state.standardFields,
          { id: state.standardFields.length + 1 },
        ],
      }));
    },

    deleteStandardField: () => {
      set((state) => ({
        standardFields:
          state.standardFields.length > 1
            ? state.standardFields.slice(0, -1)
            : state.standardFields,
      }));
    },

    setStandardField: (id: number, field: string, value: number) => {
      set((state) => ({
        standardFields: state.standardFields.map((item) =>
          item.id === id
            ? {
                ...item,
                [field]: value,
              }
            : item
        ),
      }));
    },

    resetStandardField: () =>
      set({
        standardFields: [{ id: 0 }],
      }),
  })
);
